package com.transportation.letsride.data.source

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.toStringWithCommas
import com.transportation.letsride.data.api.GoogleMapsApi
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.model.GeocodeResponse
import com.transportation.letsride.data.model.GeocodeResult
import com.transportation.letsride.data.model.GoogleMapsResponseStatus
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDataSource @Inject constructor(
    val googleMapsApi: GoogleMapsApi
) {

  fun findAddressByLocation(latLng: LatLng): Single<Address?> {
    return findAddressesByLocation(latLng)
        .map(this::fromAddressResult)
  }

  fun findAddressesByLocation(latLng: LatLng): Single<List<Address>> {
    return googleMapsApi
        .getReverseGeocode(latLng = latLng.toStringWithCommas())
        .flatMap(handleGoogleMapsApiResponseStatus())
        .map(toAddress())
  }

  private fun fromAddressResult(addresses: List<Address>): Address? {
    return addresses.filter { it.isValid() }.first()
  }

  private fun toAddress(): (List<GeocodeResult>) -> List<Address> {
    return { results -> results.map { it.toAddress() } }
  }

  private fun handleGoogleMapsApiResponseStatus(): (GeocodeResponse) -> Single<List<GeocodeResult>> {
    return { (status, results) ->
      when (status) {
        GoogleMapsResponseStatus.OK -> Single.fromCallable { results }
        else -> Single.error(Exception("Something went wrong. Status: $status"))
      }
    }
  }

}
