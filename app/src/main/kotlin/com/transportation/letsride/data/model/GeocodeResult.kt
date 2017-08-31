package com.transportation.letsride.data.model

import com.google.gson.annotations.SerializedName

data class GeocodeResult(
    @SerializedName("address_components") val addressComponents: List<AddressComponent>,
    @SerializedName("formatted_address") val formattedAddress: String,
    val geometry: Geometry,
    @SerializedName("partial_match") val partialMatch: Boolean,
    @SerializedName("place_id") val placeId: String,
    val types: List<String>
) {
  fun getStreetName() = getAddressComponentLongName("route")
  fun getStreetNumber() = getAddressComponentLongName("street_number")
  fun getPostalCode() = getAddressComponentLongName("postal_code")
  fun getSublocality() = getAddressComponentLongName("sublocality", "political")
  fun getNeighborhood() = getAddressComponentLongName("neighborhood", "political")
  fun getCity() = getAddressComponentLongName("administrative_area_level_2", "political")
  fun getState() = getAddressComponentLongName("administrative_area_level_1", "political")
  fun getCountry() = getAddressComponentLongName("country", "political")

  fun getAddressComponentLongName(vararg types: String): String {
    val filtered = addressComponents.filter { it.types.containsAll(types.asList()) }
    if (filtered.isEmpty()) return ""
    return filtered.first().longName
  }

  fun toAddress(): Address {
    return Address(
        name = "",
        country = "",
        city = "",
        latitude = 2.0,
        longitude = 2.0
    )
  }

}
