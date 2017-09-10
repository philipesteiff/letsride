package com.transportation.letsride.feature.map

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.map.ui.fragment.CustomMapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CustomMapFeatureModule {

  @PerFragment
  @ContributesAndroidInjector
  abstract fun mapFragmentInjector(): CustomMapFragment

}

