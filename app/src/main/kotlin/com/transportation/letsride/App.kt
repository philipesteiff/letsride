package com.transportation.letsride

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.transportation.letsride.common.di.component.ApplicationComponent
import com.transportation.letsride.common.di.component.DaggerApplicationComponent
import com.transportation.letsride.common.di.module.AndroidModule
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

  companion object {
    fun getRefWatcher(context: Context): RefWatcher {
      return (context as App).refWatcher
    }
  }

  @Inject
  lateinit var logger: Timber.Tree

  lateinit var appComponent: ApplicationComponent

  private lateinit var refWatcher: RefWatcher

  override fun onCreate() {
    super.onCreate()
    initInjection()
    initHelpers()
  }

  private fun initInjection() {
    appComponent = DaggerApplicationComponent.builder()
        .androidModule(AndroidModule(this))
        .build()
    appComponent.inject(this)
  }

  private fun initHelpers() {
    Timber.plant(logger)
    LeakCanary.install(this)
  }

}
