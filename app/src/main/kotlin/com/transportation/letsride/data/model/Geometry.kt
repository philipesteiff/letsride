package com.transportation.letsride.data.model

import com.google.gson.annotations.SerializedName

data class Geometry(
    val location: Location,
    @SerializedName("location_type") val locationType: String,
    val viewport: Viewport
)
