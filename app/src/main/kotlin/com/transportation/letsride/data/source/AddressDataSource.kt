package com.transportation.letsride.data.source

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.formatWithComma
import com.transportation.letsride.common.extensions.toStringWithCommas
import com.transportation.letsride.data.api.GoogleMapsApi
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.*
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDataSource @Inject constructor(
    val schedulers: SchedulerProvider,
    val googleMapsApi: GoogleMapsApi
) {

  fun findAddressByLocation(latLng: LatLng): Single<Address?> {
    return findAddressesByLocation(latLng)
        .map(this::fromAddressResult)
  }

  fun findAddressesByLocation(latLng: LatLng): Single<List<Address>> {
    return googleMapsApi
        .getReverseGeocode(latLng = latLng.toStringWithCommas())
        .flatMapObservable(handleGoogleApiResponse())
        .map(mapToAddress())
        .singleOrError()
  }

  fun reverseGeocode(placeId: String): Single<Address> {
    return googleMapsApi
        .getReverseGeocode(placeId = placeId)
        .flatMapObservable(handleGoogleApiResponse())
        .flatMapIterable { it }
        .take(1)
        .map { it.toAddress() }
        .singleOrError()

  }

  private fun handleGoogleApiResponse(): (GeocodeResponse) -> Observable<List<GeocodeResult>> {
    return { (status, result) ->
      when (status) {
        GoogleMapsResponseStatus.OK -> Observable.just(result)
        GoogleMapsResponseStatus.ZERO_RESULTS -> Observable.empty()
        else -> Observable.error(Exception("Something went wrong. Status: $status"))
      }
    }
  }

  private fun fromAddressResult(addresses: List<Address>): Address? {
    return addresses.firstOrNull()
  }

  private fun mapToAddress(): (List<GeocodeResult>) -> List<Address> {
    return { results -> results.map { it.toAddress() } }
  }

  fun query(input: String, options: AutoCompleteOptions): Single<List<Prediction>> {
    return googleMapsApi.getPlaceAutocomplete(
        input = input,
        location = options.location?.formatWithComma(),
        radius = options.radius,
        language = options.language)
        .flatMap { (status, predictions) ->
          when (status) {
            GoogleMapsResponseStatus.OK -> Single.fromCallable { predictions }
            else -> Single.error(Exception("Something went wrong. Status: $status"))
          }
        }
        .observeOn(schedulers.ui())
        .subscribeOn(schedulers.io())
  }

}
