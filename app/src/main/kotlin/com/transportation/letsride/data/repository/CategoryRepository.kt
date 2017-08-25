package com.transportation.letsride.data.repository

import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.model.Estimate
import com.transportation.letsride.data.model.Estimates
import com.transportation.letsride.data.model.JourneyEstimate
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

class CategoryRepository(
    val journeyApi: JourneyApi
) : Repository.Category {

  override fun estimates(journeyEstimate: JourneyEstimate): Single<Estimates> {
    return journeyApi.estimate(journeyEstimate)
        .flatMap { response ->
          when {
            response.isSuccessful -> Single.fromCallable { response.body() }
            else -> Single.error(Exception(""))
          }
        }
  }

}
