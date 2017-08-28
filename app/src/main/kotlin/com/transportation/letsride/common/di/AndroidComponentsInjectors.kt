package com.transportation.letsride.common.di

import android.app.Activity
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector

interface ActivityInjector : HasActivityInjector {
  var activityInjector: DispatchingAndroidInjector<Activity>
  override fun activityInjector() = activityInjector
}

interface FragmentInjector : HasSupportFragmentInjector {
  var fragmentInjector: DispatchingAndroidInjector<Fragment>
  override fun supportFragmentInjector() = fragmentInjector
}
