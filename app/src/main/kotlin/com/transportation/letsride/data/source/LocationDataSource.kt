package com.transportation.letsride.data.source

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.transportation.letsride.common.util.unsafeLazy
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataSource @Inject constructor(
    private val googleApiClient: GoogleApiClient
) : LocationListener,
    GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

  private val locationRequest: LocationRequest by unsafeLazy {
    LocationRequest()
        .setPriority(PRIORITY_HIGH_ACCURACY)
        .setFastestInterval(FASTEST_INTERVAL_UPDATES)
        .setInterval(INTERVAL_UPDATES)
  }

  private var locations = BehaviorSubject.create<Location>()

  fun getLocationsStream(): Observable<Location> = locations
      .doOnNext { Timber.d("Location: $it") }
      .doOnSubscribe { Timber.d("New subscription") }
      .doOnSubscribe { connect() }
      .doOnDispose { disconnect() }

  private fun connect() {
    if (!googleApiClient.isConnected) {
      googleApiClient.registerConnectionCallbacks(this@LocationDataSource)
      googleApiClient.connect()

      Timber.d("Connected")
    }
  }

  private fun disconnect() {
    if (!locations.hasObservers()) {
      googleApiClient.disconnect()
      Timber.d("Disconnected")
    } else {
      Timber.d("Has not disconnected yet. Reason: has observers.")
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
    LocationSourceConnectionFailedException(connectionResult.errorMessage.orEmpty())
        .let { locations.onError(it) }
  }

  override fun onConnectionSuspended(connectionSuspended: Int) {}

  @SuppressLint("MissingPermission")
  private fun startLocationUpdates() {
    if (googleApiClient.isConnected)
      LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
  }

  @SuppressLint("MissingPermission")
  private fun sendLastLocation() {
    val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
    sendLocation(lastLocation)
  }

  private fun sendLocation(newLocation: Location?) {
    newLocation?.let { locations.onNext(it) }
  }

  companion object {
    const val INTERVAL_UPDATES = 10000L // 10 seconds
    const val FASTEST_INTERVAL_UPDATES = 5000L // 5 seconds
  }

}

class LocationSourceConnectionFailedException(message: String) : Exception(message)

