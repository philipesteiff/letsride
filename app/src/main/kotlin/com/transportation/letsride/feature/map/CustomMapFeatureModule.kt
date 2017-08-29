package com.transportation.letsride.feature.map

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.map.fragment.CustomMapFragment
import com.transportation.letsride.feature.map.fragment.MapControlsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CustomMapFeatureModule {

  @PerFragment
  @ContributesAndroidInjector
  abstract fun mapFragmentInjector(): CustomMapFragment

  @PerFragment
  @ContributesAndroidInjector
  abstract fun mapControlsFragmentInjector(): MapControlsFragment

}

