package com.transportation.letsride.data.model

data class Address(
    val name: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
) {

  fun isValid() = true

}
