package com.transportation.letsride.feature.pickup.di

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.map.di.CustomMapModule
import com.transportation.letsride.feature.map.fragment.CustomMapFragment
import com.transportation.letsride.feature.map.fragment.MapControlsFragment
import com.transportation.letsride.feature.route.di.RouteFragmentModule
import com.transportation.letsride.feature.route.fragment.RouteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PickupActivityModule {

  @PerFragment
  @ContributesAndroidInjector(modules = arrayOf(CustomMapModule::class))
  abstract fun mapFragmentInjector(): CustomMapFragment

  @PerFragment
  @ContributesAndroidInjector(modules = arrayOf(CustomMapModule::class))
  abstract fun mapControlsFragmentInjector(): MapControlsFragment

  @PerFragment
  @ContributesAndroidInjector(modules = arrayOf(RouteFragmentModule::class))
  abstract fun routeFragmentInjector(): RouteFragment

}
