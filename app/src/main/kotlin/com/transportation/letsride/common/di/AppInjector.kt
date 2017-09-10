package com.transportation.letsride.common.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.transportation.letsride.App
import com.transportation.letsride.common.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

interface AppInjector {

  fun initInjection(app: App) {
    inject(app)
    registerForAutomaticInjection(app)
  }

  private fun registerForAutomaticInjection(app: App) {
    app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        AndroidInjection.inject(activity)
        handleFragment(activity)
      }

      override fun onActivityPaused(activity: Activity) {}
      override fun onActivityResumed(activity: Activity) {}
      override fun onActivityStarted(activity: Activity) {}
      override fun onActivityDestroyed(activity: Activity) {}
      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}
      override fun onActivityStopped(activity: Activity) {}
    })
  }

  private fun handleFragment(activity: Activity) {
    if (activity is FragmentActivity) {
      activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
          object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(fm: FragmentManager, fragment: Fragment, savedInstanceState: Bundle?) {
              if (fragment is Injectable)
                AndroidSupportInjection.inject(fragment)
            }
          }, true)
    }
  }

  fun inject(app: App) {
    DaggerApplicationComponent.builder()
        .application(app)
        .build()
        .inject(app)
  }

}
