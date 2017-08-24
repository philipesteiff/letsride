package com.transportation.letsride.common.ui.fragment

import android.support.v4.app.Fragment
import com.squareup.leakcanary.RefWatcher
import com.transportation.letsride.App

class BaseFragment : Fragment() {

  override fun onDestroy() {
    super.onDestroy()
    val refWatcher: RefWatcher = App.getRefWatcher(activity.application)
    refWatcher.watch(this)
  }

}
