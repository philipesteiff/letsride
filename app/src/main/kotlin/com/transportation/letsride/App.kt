package com.transportation.letsride

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.transportation.letsride.common.di.ActivityInjector
import com.transportation.letsride.common.di.AppInjector
import com.transportation.letsride.common.di.Injectable
import com.transportation.letsride.common.di.component.DaggerApplicationComponent
import com.transportation.letsride.common.util.Logger
import com.transportation.letsride.common.util.MemoryLeakAnalyzer
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
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
