package com.transportation.letsride.data.model

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class Geometry(
    val location: LatLng,
    @SerializedName("location_type") val locationType: String,
    val viewport: Viewport
)
