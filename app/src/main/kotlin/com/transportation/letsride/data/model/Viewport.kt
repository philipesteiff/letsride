package com.transportation.letsride.data.model

import com.google.android.gms.maps.model.LatLng

data class Viewport(
    val northeast: LatLng,
    val southwest: LatLng
)
