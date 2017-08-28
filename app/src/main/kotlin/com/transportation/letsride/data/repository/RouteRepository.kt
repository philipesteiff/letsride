package com.transportation.letsride.data.repository

import com.transportation.letsride.common.extensions.formatWithComma
import com.transportation.letsride.data.api.GoogleMapsApi
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.AutocompleteOptions
import com.transportation.letsride.data.model.PlaceAutocompleteResponse
import com.transportation.letsride.data.model.Prediction
import io.reactivex.Single

class RouteRepository(
    val scheduler: SchedulerProvider,
    val googleMapsApi: GoogleMapsApi
) : Repository.Route {

  override fun query(input: String, options: AutocompleteOptions): Single<List<Prediction>> {
    return googleMapsApi.getPlaceAutocomplete(
        key = GoogleMapsApi.GOOGLE_MAPS_API_AUTOCOMPLETE_KEY,
        input = input,
        location = options.location?.formatWithComma(),
        radius = options.radius,
        language = options.language)
        .flatMap { (status, predictions) ->
          when (status) {
            PlaceAutocompleteResponse.OK -> Single.fromCallable { predictions }
            else -> Single.error(Exception("Something went wrong. Status: $status"))
          }
        }
        .observeOn(scheduler.ui())
        .subscribeOn(scheduler.io())
  }

}
