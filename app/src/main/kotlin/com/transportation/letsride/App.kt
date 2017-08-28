package com.transportation.letsride

import android.app.Activity
import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.transportation.letsride.common.di.ActivityInjector
import com.transportation.letsride.common.di.component.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), ActivityInjector {

  @Inject
  override lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  @Inject
  lateinit var logger: Timber.Tree

  private lateinit var refWatcher: RefWatcher

  override fun onCreate() {
    super.onCreate()
    initInjection()
    initHelpers()
  }

  private fun initInjection() {
    DaggerApplicationComponent.builder()
        .create(this)
        .inject(this)
  }

  private fun initHelpers() {
    Timber.plant(logger)
    if (LeakCanary.isInAnalyzerProcess(this)) {
      // This process is dedicated to LeakCanary for heap analysis.
      // You should not init your app in this process.
      return
    }
    refWatcher = LeakCanary.install(this);
    // Normal app init code...
  }

  companion object {
    fun getRefWatcher(context: Context): RefWatcher {
      return (context as App).refWatcher
    }
  }

}
