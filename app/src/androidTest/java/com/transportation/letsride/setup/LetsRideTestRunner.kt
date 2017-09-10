package com.transportation.letsride.setup

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

class LetsRideTestRunner : AndroidJUnitRunner() {
  @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
  override fun newApplication(classLoader: ClassLoader, className: String, context: Context): Application {
    return super.newApplication(classLoader, TestApp::class.java.name, context)
  }
}
