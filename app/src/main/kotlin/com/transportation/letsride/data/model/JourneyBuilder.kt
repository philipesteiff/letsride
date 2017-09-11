package com.transportation.letsride.data.model

object JourneyBuilder {

  fun buildJourneyEstimate(pickupPinPoint: PinPoint?, dropOffPinPoint: PinPoint?): JourneyEstimate {
    val pickupStop = buildStop(pickupPinPoint)
    val dropOffStop = buildStop(dropOffPinPoint)
    return listOf(pickupStop, dropOffStop)
        .let { stops -> JourneyEstimate(stops) }
  }

  fun buildStop(pinPoint: PinPoint?): Stop {
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
