package com.transportation.letsride

import android.app.Activity
import android.app.Application
import com.transportation.letsride.common.di.ActivityInjector
import com.transportation.letsride.common.di.AppInjector
import com.transportation.letsride.common.util.Logger
import com.transportation.letsride.common.util.MemoryLeakAnalyzer
import dagger.android.DispatchingAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(),
    AppInjector,
    ActivityInjector,
    Logger,
    MemoryLeakAnalyzer {

  @Inject
  override lateinit var activityInjector: DispatchingAndroidInjector<Activity>

  @Inject
  override lateinit var logger: Timber.Tree

  override fun onCreate() {
    super.onCreate()
    initInjection(this)
    initAnalyzerProcess(this)
    initLogger(logger)
  }

}
