package com.transportation.letsride.common.ui.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.transportation.letsride.BuildConfig
import com.transportation.letsride.common.di.FragmentInjector
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    enableStrictMode()
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    window.setBackgroundDrawable(null)
  }

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
