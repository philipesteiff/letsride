package com.transportation.letsride.data.model

data class PlaceAutocompleteResponse(
    val status: String,
    val predictions: List<Prediction>
) {
  companion object {
    const val OK = "OK"
    const val ZERO_RESULTS = "ZERO_RESULTS"
    const val OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT"
    const val REQUEST_DENIED = "REQUEST_DENIED"
    const val INVALID_REQUEST = "INVALID_REQUEST"
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
  }
}
