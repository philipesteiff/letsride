package com.transportation.letsride.data.repository

import com.transportation.letsride.data.model.AutocompleteOptions
import com.transportation.letsride.data.model.Estimates
import com.transportation.letsride.data.model.JourneyEstimate
import com.transportation.letsride.data.model.Prediction
import io.reactivex.Single

object Repository {

  interface Category {
    fun estimates(journeyEstimate: JourneyEstimate): Single<Estimates>
  }

  interface Route {
    fun query(input: String, options: AutocompleteOptions): Single<List<Prediction>>
  }

}
