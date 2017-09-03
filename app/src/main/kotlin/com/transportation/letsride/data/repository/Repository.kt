package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.*
import io.reactivex.Observable
import io.reactivex.Single

object Repository {

  interface Location {
    fun location(): Observable<android.location.Location>
    fun getAddressFromLocation(latLng: LatLng): Single<Address?>
  }

  interface Category {
    fun estimates(journeyEstimate: JourneyEstimate): Single<Estimates>
  }

  interface Route {
    fun query(input: String, options: AutocompleteOptions): Single<List<Prediction>>
  }

}
