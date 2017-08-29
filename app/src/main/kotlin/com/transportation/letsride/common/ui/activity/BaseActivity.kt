package com.transportation.letsride.common.ui.activity

import android.annotation.TargetApi
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import com.transportation.letsride.BuildConfig

abstract class BaseActivity : AppCompatActivity(), LifecycleRegistryOwner {

  private val lifecycleRegistry = LifecycleRegistry(this)

  override fun onCreate(savedInstanceState: Bundle?) {
    enableStrictMode()
    super.onCreate(savedInstanceState)
    window.setBackgroundDrawable(null)
  }

  override fun getLifecycle() = lifecycleRegistry

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  private fun enableStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
          .detectAll()
          .penaltyLog()
          .build())
      StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
          .detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .penaltyDeath()
          .build())
    }
  }
}
