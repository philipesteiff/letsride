package com.transportation.letsride.data.repository

import com.transportation.letsride.data.model.Estimate
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.source.EstimatesDataSource
import io.reactivex.Single

class JourneyRepositoryImpl(
    val estimatesDataSource: EstimatesDataSource
) : JourneyRepository {

  override fun estimates(pickupPinPoint: PinPoint?, dropOffPinPoint: PinPoint?): Single<List<Estimate>> {
    return estimatesDataSource.estimates(pickupPinPoint, dropOffPinPoint)
  }

}
