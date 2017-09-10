package com.transportation.letsride.common.extensions

import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewTreeObserver

fun View.onLayoutReady(onReady: () -> Unit) {
  if (ViewCompat.isLaidOut(this)) {
    onReady()
  } else {
    viewTreeObserver.addOnGlobalLayoutListener(
        object : ViewTreeObserver.OnGlobalLayoutListener {
          override fun onGlobalLayout() {
            onReady()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
          }
        }
    )
  }
}
