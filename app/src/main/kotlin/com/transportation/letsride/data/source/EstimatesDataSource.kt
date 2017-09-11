package com.transportation.letsride.data.source

import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.model.Estimate
import com.transportation.letsride.data.model.JourneyBuilder
import com.transportation.letsride.data.model.PinPoint
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstimatesDataSource @Inject constructor(
    private val journeyApi: JourneyApi
) {

  fun estimates(pickupPinPoint: PinPoint?, dropOffPinPoint: PinPoint?): Single<List<Estimate>> {
    val journeyEstimate = JourneyBuilder.buildJourneyEstimate(pickupPinPoint, dropOffPinPoint)
    return journeyApi.estimate(journeyEstimate)
  }

}
