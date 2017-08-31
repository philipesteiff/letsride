package com.transportation.letsride.common.extensions

import android.location.Location
import com.google.android.gms.maps.model.LatLng

infix fun Location.isDifferent(location: Location): Boolean {
  return location.latitude != this.latitude
      && location.longitude != this.longitude
}

fun LatLng.toStringWithCommas() = "$latitude,$longitude"
