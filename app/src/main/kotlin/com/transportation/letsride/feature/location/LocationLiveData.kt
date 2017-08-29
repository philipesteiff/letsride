package com.transportation.letsride.feature.location

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.transportation.letsride.common.util.unsafeLazy

class LocationLiveData(
    private val googleApiClient: GoogleApiClient
) : LiveData<LocationLiveData.LocationData>(),
    LocationListener,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

  private val lastLocation: Location
    @SuppressLint("MissingPermission")
    get() = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)


  private val locationRequest: LocationRequest by unsafeLazy {
    LocationRequest()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
  }

  private val newLocation = LocationData.NewLocation(Location("empty"))

  override fun onActive() {
    super.onActive()
    hasLocationPermission {
      isGoogleApiClientConnected {
        sendLastLocation()
        startLocationUpdates()
      }
    }
  }

  private fun sendLastLocation() {
    lastLocation
        .takeIf { value == null }
        .let { location ->
          if (location != null) {
            newLocation.location = location
            value = newLocation
          }
        }
  }

  override fun onInactive() {
    super.onInactive()
    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
  }

  private fun isGoogleApiClientConnected(callback: () -> Unit) {
    if (googleApiClient.isConnected)
      callback.invoke()
    else
      googleApiClient.registerConnectionCallbacks(this)
  }


  override fun onLocationChanged(location: Location) {
    value = LocationData.NewLocation(location)
  }

  override fun onConnectionFailed(connectionResult: ConnectionResult) {
    value = LocationData.ConnectionFailed()
  }

  override fun onConnected(bundle: Bundle?) {
    googleApiClient.unregisterConnectionCallbacks(this)
    onActive()
  }

  override fun onConnectionSuspended(connectionSuspended: Int) {

  }

  @SuppressLint("MissingPermission")
  private fun startLocationUpdates() {
    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
  }

  private fun hasLocationPermission(callback: () -> Unit) {
    if (ActivityCompat.checkSelfPermission(googleApiClient.context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
      callback.invoke()
    else
      LocationData.PermissionRequired()
  }

  sealed class LocationData {
    class NewLocation(var location: Location) : LocationData()
    class PermissionRequired : LocationData()
    class ConnectionFailed : LocationData()
  }

}


