package com.transportation.letsride.feature.route

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.route.fragment.RouteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RouteFeatureModule {

  @PerFragment
  @ContributesAndroidInjector
  abstract fun routeFragmentInjector(): RouteFragment

}
