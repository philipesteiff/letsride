package com.transportation.letsride.data.model

import com.google.gson.annotations.SerializedName

data class Vehicle(
    @SerializedName("_id") val id: String,
    @SerializedName("name`") val name: String,
    @SerializedName("short_name") val shortName: String,
    @SerializedName("description") val description: String,
    @SerializedName("icons") val icons: Icons,
    @SerializedName("icon") val icon: String
)

