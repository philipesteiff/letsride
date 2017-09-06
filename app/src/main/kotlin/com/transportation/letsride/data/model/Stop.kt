package com.transportation.letsride.data.model

import com.google.gson.annotations.SerializedName

data class Stop(
    @SerializedName("loc") val location: List<Double>,
    @SerializedName("name") val name: String,
    @SerializedName("addr") val address: String,
    @SerializedName("num") val number: String,
    @SerializedName("city") val city: String,
    @SerializedName("country") val country: String
)
