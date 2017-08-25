package com.transportation.letsride.data.repository

import com.transportation.letsride.data.model.JourneyEstimate

object Repository {

  interface Category {
    fun estimates(journeyEstimate: JourneyEstimate)
  }

}
