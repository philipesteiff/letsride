package com.transportation.letsride.data.source

import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.model.Estimate
import com.transportation.letsride.data.model.JourneyEstimate
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.model.Stop
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstimatesDataSource @Inject constructor(
    private val journeyApi: JourneyApi
) {

  fun estimates(pickupPinPoint: PinPoint?, dropOffPinPoint: PinPoint?): Single<List<Estimate>> {
    val journeyEstimate = buildJourney(pickupPinPoint, dropOffPinPoint)
    return journeyApi.estimate(journeyEstimate)
        .flatMap { response ->
          when {
            response.isSuccessful -> Single.fromCallable { response.body() }
            else -> Single.error(Exception("Failed to receive estimates."))
          }
        }
  }

  private fun buildJourney(pickupPinPoint: PinPoint?, dropOffPinPoint: PinPoint?): JourneyEstimate {
    val pickupStop = buildStop(pickupPinPoint)
    val dropOffStop = buildStop(dropOffPinPoint)
    return listOf(pickupStop, dropOffStop)
        .let { stops -> JourneyEstimate(stops) }
  }

  private fun buildStop(pinPoint: PinPoint?): Stop {
    return pinPoint?.let {
      Stop(
          location = listOf(it.latitude, it.longitude),
          name = pinPoint.name,
          address = pinPoint.name,
          number = "s/n",
          city = pinPoint.city,
          country = pinPoint.country
      )
    } ?: throw IllegalStateException("Ops, pinPoint is null.")
  }

}
