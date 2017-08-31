package com.transportation.letsride.data.model

data class GeocodeResponse(
    val status: GoogleMapsResponseStatus,
    val results: List<GeocodeResult> = emptyList()
)
