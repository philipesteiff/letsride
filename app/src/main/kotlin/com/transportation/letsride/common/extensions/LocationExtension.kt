package com.transportation.letsride.common.extensions

import android.location.Location

infix fun Location.isDifferent(location: Location): Boolean {
  return location.latitude != this.latitude
      && location.longitude != this.longitude
}
