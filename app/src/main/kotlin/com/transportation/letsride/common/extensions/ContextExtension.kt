package com.transportation.letsride.common.extensions

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat

fun Context.getColorCompat(@ColorRes resId: Int): Int {
  return ContextCompat.getColor(this, resId)
}
