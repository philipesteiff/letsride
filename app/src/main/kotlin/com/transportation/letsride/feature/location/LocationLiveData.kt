package com.transportation.letsride.feature.location

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.*
import com.google.android.gms.location.LocationServices
import com.transportation.letsride.common.extensions.isDifferent
import com.transportation.letsride.common.util.unsafeLazy

class LocationLiveData(
    private val googleApiClient: GoogleApiClient
) : LiveData<LocationData>(),
    LocationListener,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

  private val locationRequest: LocationRequest by unsafeLazy {
    LocationRequest()
        .setPriority(PRIORITY_HIGH_ACCURACY)
        .setFastestInterval(FASTEST_INTERVAL_UPDATES)
        .setInterval(INTERVAL_UPDATES)
  }

  private val currentLocationData = LocationData.NewLocation(Location("empty"))

  init {
    googleApiClient.registerConnectionCallbacks(this)
  }

  override fun onActive() {
    super.onActive()
    connect()
  }

  override fun onInactive() {
    super.onInactive()
    disconnect()
  }

  private fun connect() {
    googleApiClient.takeUnless { it.isConnected }?.connect()
  }

  private fun disconnect() {
    if (googleApiClient.isConnected)
      LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)

    googleApiClient.disconnect()
  }

  override fun onConnected(bundle: Bundle?) {
    googleApiClient.unregisterConnectionCallbacks(this)
    sendLastLocation()
    startLocationUpdates()
  }

  override fun onLocationChanged(location: Location) {
    sendLocation(location)
  }

  override fun onConnectionFailed(connectionResult: ConnectionResult) {
    value = LocationData.ConnectionFailed()
  }

  override fun onConnectionSuspended(connectionSuspended: Int) {

  }

  @SuppressLint("MissingPermission")
  private fun startLocationUpdates() {
    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
  }

  @SuppressLint("MissingPermission")
  private fun sendLastLocation() {
    val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
    val currentLocation = currentLocationData.location
    lastLocation
        .takeIf { it isDifferent currentLocation }
        .let { sendLocation(it) }
  }

  private fun sendLocation(newLocation: Location?) {
    newLocation?.let {
      currentLocationData
          .apply { location = it }
          .run { value = this }
    }
  }

  companion object {
    const val INTERVAL_UPDATES = 10000L // 10 seconds
    const val FASTEST_INTERVAL_UPDATES = 5000L // 5 seconds
  }

}

sealed class LocationData {
  class NewLocation(var location: Location) : LocationData()
  class ConnectionFailed : LocationData()
}


