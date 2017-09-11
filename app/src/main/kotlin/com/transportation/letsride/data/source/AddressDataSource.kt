package com.transportation.letsride.data.source

import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.PinPoint
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDataSource @Inject constructor(
    private val geocoder: Geocoder
) {

  fun findAddressByLocation(latLng: LatLng, maxResults: Int = DEFAULT_MAX_RESULTS): Maybe<PinPoint> {
    return findAddresses(latLng, maxResults)
        .map { addresses -> addresses.firstOrNull() }
  }

  fun findAddresses(input: String, maxResults: Int = DEFAULT_MAX_RESULTS): Maybe<List<PinPoint>> {
    return Maybe.fromCallable { geocoder.getFromLocationName(input, maxResults) }
        .map(toPinPoints())
  }

  fun findAddresses(latLng: LatLng, maxResults: Int = DEFAULT_MAX_RESULTS): Maybe<List<PinPoint>> {
    return Maybe.fromCallable { geocoder.getFromLocation(latLng.latitude, latLng.longitude, maxResults) }
        .map(toPinPoints())
  }

  private fun toPinPoints() = { addresses: List<Address> -> addresses.map { PinPoint(it) } }

  companion object {
    const val DEFAULT_MAX_RESULTS = 10
  }

}
