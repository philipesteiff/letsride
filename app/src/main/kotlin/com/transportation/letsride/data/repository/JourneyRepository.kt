package com.transportation.letsride.data.repository

import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.model.Estimate
import io.reactivex.Single

interface JourneyRepository {
  fun estimates(pickupPinPoint: PinPoint?, dropOffPinPoint: PinPoint?): Single<List<Estimate>>
}
