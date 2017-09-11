package com.transportation.letsride

import android.support.test.InstrumentationRegistry
import com.transportation.letsride.common.di.component.ApplicationComponent
import com.transportation.letsride.common.di.module.AppModule
import it.cosenonjaviste.daggermock.DaggerMockRule

class EspressoDaggerMockRule : DaggerMockRule<ApplicationComponent>(ApplicationComponent::class.java, AppModule()) {
  init {
    customizeBuilder { builder: ApplicationComponent.Builder -> builder.application(getApp()) }
    set { component -> component.inject(getApp()) }
  }

  companion object {
    fun getApp(): App = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App
  }
}
