package com.transportation.letsride.data.model

import com.google.gson.annotations.SerializedName

data class Estimate(
    @SerializedName("vehicle_type") val vehicle: Vehicle,
    @SerializedName("total_price") val totalPrice: Int,
    @SerializedName("price_formatted") val formattedPrice: String?,
    @SerializedName("currency") val currency: String,
    @SerializedName("currency_symbol") val currencySymbol: String
)
