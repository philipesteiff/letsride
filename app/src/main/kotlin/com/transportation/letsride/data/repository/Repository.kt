package com.transportation.letsride.data.repository

import com.transportation.letsride.data.model.Estimates
import com.transportation.letsride.data.model.JourneyEstimate
import io.reactivex.Single

object Repository {

  interface Category {
    fun estimates(journeyEstimate: JourneyEstimate): Single<Estimates>
  }

}
