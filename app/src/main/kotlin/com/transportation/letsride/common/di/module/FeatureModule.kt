package com.transportation.letsride.common.di.module

import com.transportation.letsride.feature.categories.CategoriesFeatureModule
import com.transportation.letsride.feature.pickup.PickupFeatureModule
import com.transportation.letsride.feature.search.SearchAddressFeatureModule
import dagger.Module

@Module(includes = arrayOf(
    PickupFeatureModule::class,
    SearchAddressFeatureModule::class,
    CategoriesFeatureModule::class
))
abstract class FeatureModule
