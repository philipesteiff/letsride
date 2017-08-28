package com.transportation.letsride.common

import android.os.Bundle

interface Presentable<in V> {
  fun bindView(view: V)
  fun onViewReady(savedInstanceState: Bundle?, extras: Bundle?)
  fun onSaveInstanceState(bundle: Bundle?)
}
