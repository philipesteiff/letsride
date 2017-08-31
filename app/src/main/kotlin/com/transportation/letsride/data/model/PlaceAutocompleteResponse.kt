package com.transportation.letsride.data.model

data class PlaceAutocompleteResponse(
    val status: GoogleMapsResponseStatus,
    val predictions: List<Prediction>
)
