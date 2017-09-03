package com.transportation.letsride.common.extensions

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun LatLng.toStringWithCommas() = "$latitude,$longitude"

fun Location.toLatLng() = LatLng(latitude, longitude)
