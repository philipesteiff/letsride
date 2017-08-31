package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.model.AutocompleteOptions
import com.transportation.letsride.data.model.Estimates
import com.transportation.letsride.data.model.JourneyEstimate
import com.transportation.letsride.data.model.Prediction
import com.transportation.letsride.data.source.LocationSourceResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

object Repository {

  interface Location {
    fun location(): Observable<LocationSourceResponse>
    fun getAddressFromLocation(latLng: LatLng): Single<Address?>
  }

  interface Category {
    fun estimates(journeyEstimate: JourneyEstimate): Single<Estimates>
  }

  interface Route {
    fun query(input: String, options: AutocompleteOptions): Single<List<Prediction>>
  }

}
