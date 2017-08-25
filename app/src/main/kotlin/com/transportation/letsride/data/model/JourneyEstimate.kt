package com.transportation.letsride.data.model

import com.google.gson.annotations.SerializedName

data class JourneyEstimate(
    @SerializedName("stops") var stops: List<Stop>,
    @SerializedName("start_at") val startDate: String? = null
)
