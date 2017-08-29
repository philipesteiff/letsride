package com.transportation.letsride.feature.pickup

import com.transportation.letsride.common.di.scopes.PerActivity
import com.transportation.letsride.feature.map.CustomMapFeatureModule
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import com.transportation.letsride.feature.route.RouteFeatureModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PickupFeatureModule {

  @PerActivity
  @ContributesAndroidInjector(modules = arrayOf(
      CustomMapFeatureModule::class,
      RouteFeatureModule::class
  ))
  abstract fun pickupActivity(): PickupActivity

}
