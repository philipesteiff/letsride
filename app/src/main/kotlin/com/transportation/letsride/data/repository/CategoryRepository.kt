package com.transportation.letsride.data.repository

import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.model.JourneyEstimate
import timber.log.Timber

class CategoryRepository(
    val journeyApi: JourneyApi
) : Repository.Category {

  override fun estimates(journeyEstimate: JourneyEstimate) {
    journeyApi.estimate(journeyEstimate)
        .subscribe(
            { Timber.d(it.toString()) },
            { Timber.e(it) }
        )
  }

}
