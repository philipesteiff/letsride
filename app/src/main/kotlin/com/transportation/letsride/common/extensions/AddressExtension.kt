package com.transportation.letsride.common.extensions

import android.location.Address

fun Address.getThoroughfareOrFullAddressOrEmpty(): String {
  return thoroughfare ?: getAddressLine(0) ?: ""
}

fun Address.getFullAddressOrThoroughfareOrEmpty(): String {
  return getAddressLine(0) ?: thoroughfare ?: ""
}
