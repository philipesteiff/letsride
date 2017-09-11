package com.transportation.letsride.data.model

import com.google.gson.annotations.SerializedName

data class Eta(
    @SerializedName("min") val minimum: Int,
    @SerializedName("max") val maximum: Int,
    @SerializedName("formatted") val formatted: String
)
