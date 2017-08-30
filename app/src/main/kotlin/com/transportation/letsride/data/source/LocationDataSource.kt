package com.transportation.letsride.data.source

import android.annotation.SuppressLint
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
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataSource @Inject constructor(
    private val googleApiClient: GoogleApiClient
) : LocationListener,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

  private val currentLocationData = LocationSourceResponse.NewLocation(Location("empty"))

  private val locationRequest: LocationRequest by unsafeLazy {
    LocationRequest()
        .setPriority(PRIORITY_HIGH_ACCURACY)
        .setFastestInterval(FASTEST_INTERVAL_UPDATES)
        .setInterval(INTERVAL_UPDATES)
  }

  var locations = PublishSubject.create<LocationSourceResponse>().apply {
    doOnSubscribe { connect() }
    doOnDispose { if (!hasObservers()) disconnect() }
  }

  private fun connect() {
    googleApiClient.run {
      if (!isConnected) {
        registerConnectionCallbacks(this@LocationDataSource)
        connect()
      }
    }
  }

  private fun disconnect() {
    googleApiClient.run {
      if (isConnected)
        LocationServices.FusedLocationApi.removeLocationUpdates(this, this@LocationDataSource)

      disconnect()
    }
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
    locations.onNext(LocationSourceResponse.ConnectionFailed())
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
          .let { locations.onNext(it) }
    }
  }

  companion object {
    const val INTERVAL_UPDATES = 10000L // 10 seconds
    const val FASTEST_INTERVAL_UPDATES = 5000L // 5 seconds
  }

}

sealed class LocationSourceResponse {
  class NewLocation(var location: Location) : LocationSourceResponse()
  class ConnectionFailed : LocationSourceResponse()
}


