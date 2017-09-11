package com.transportation.letsride.feature.pickup

import com.transportation.letsride.common.di.scopes.PerActivity
import com.transportation.letsride.feature.map.CustomMapFeatureModule
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import com.transportation.letsride.feature.pickupdropoff.PickupDropOffFeatureModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PickupFeatureModule {

  @PerActivity
  @ContributesAndroidInjector(modules = arrayOf(
      CustomMapFeatureModule::class,
      PickupDropOffFeatureModule::class
  ))
  abstract fun pickupActivity(): PickupActivity

}
