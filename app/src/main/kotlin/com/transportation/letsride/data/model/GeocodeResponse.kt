package com.transportation.letsride.data.model

data class GeocodeResponse(
    val status: String,
    val results: List<GeocodeResult>?
)
