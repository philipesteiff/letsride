package com.transportation.letsride.common.ui.fragment

import android.content.Context
import android.support.v4.app.Fragment
import com.squareup.leakcanary.RefWatcher
import com.transportation.letsride.App
import dagger.android.support.AndroidSupportInjection

open class BaseFragment : Fragment() {

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onDestroy() {
    super.onDestroy()
    val refWatcher: RefWatcher = App.getRefWatcher(activity.application)
    refWatcher.watch(this)
  }

}
