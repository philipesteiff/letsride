package com.transportation.letsride.feature.pickupdropoff

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.pickupdropoff.ui.fragment.PickupDropOffFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PickupDropOffFeatureModule {

  @PerFragment
  @ContributesAndroidInjector
  abstract fun routeFragmentInjector(): PickupDropOffFragment

}
