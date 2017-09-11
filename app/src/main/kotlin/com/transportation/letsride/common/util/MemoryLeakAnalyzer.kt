package com.transportation.letsride.common.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.transportation.letsride.BuildConfig

interface MemoryLeakAnalyzer {

  fun initAnalyzerProcess(application: Application) {
    if (!BuildConfig.ENABLE_DEBUG_MONITORS) return

    if (LeakCanary.isInAnalyzerProcess(application)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    val refWatcher = LeakCanary.install(application)
    // Normal app init code...

    registerInLifecycle(application, refWatcher)
  }

  private fun registerInLifecycle(application: Application, refWatcher: RefWatcher) {
    application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        handleActivity(activity, refWatcher)
      }

      override fun onActivityPaused(activity: Activity) {}
      override fun onActivityResumed(activity: Activity) {}
      override fun onActivityStarted(activity: Activity) {}
      override fun onActivityDestroyed(activity: Activity) {}
      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}
      override fun onActivityStopped(activity: Activity) {}
    })
  }

  fun handleActivity(activity: Activity, refWatcher: RefWatcher) {
    if (activity is FragmentActivity) {
      activity.supportFragmentManager
          .registerFragmentLifecycleCallbacks(
              object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentDestroyed(fm: FragmentManager?, f: Fragment?) {
                  super.onFragmentDestroyed(fm, f)
                  refWatcher.watch(this)
                }
              }, true)
    }
  }

}
