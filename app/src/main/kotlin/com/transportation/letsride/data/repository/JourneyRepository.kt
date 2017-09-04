package com.transportation.letsride.data.repository

import com.transportation.letsride.data.model.Estimates
import com.transportation.letsride.data.model.JourneyEstimate
import io.reactivex.Single

interface JourneyRepository {
  fun estimates(journeyEstimate: JourneyEstimate): Single<Estimates>
}
