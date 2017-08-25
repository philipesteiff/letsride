package com.transportation.letsride.data.api

import com.transportation.letsride.data.model.Estimates
import com.transportation.letsride.data.model.JourneyEstimate
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface JourneyApi {

  @POST("v2/estimate")
  fun estimate(@Body journeyEstimate: JourneyEstimate): Single<Response<Estimates>>

}

