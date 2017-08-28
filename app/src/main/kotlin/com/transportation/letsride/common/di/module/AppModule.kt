package com.transportation.letsride.common.di.module

import com.transportation.letsride.common.di.scopes.PerActivity
import com.transportation.letsride.feature.pickup.di.PickupActivityModule
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import com.transportation.letsride.feature.search.activity.SearchAddressActivity
import com.transportation.letsride.feature.search.di.SearchAddressActivityModule
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = arrayOf(
    AndroidInjectionModule::class,
    AndroidModule::class,
    LoggerModule::class,
    RepositoryModule::class,
    NetworkModule::class,
    ApiModule::class,
    ParsersModule::class
))
abstract class AppModule {

  @PerActivity
  @ContributesAndroidInjector(modules = arrayOf(PickupActivityModule::class))
  abstract fun pickupActivity(): PickupActivity

  @PerActivity
  @ContributesAndroidInjector(modules = arrayOf(SearchAddressActivityModule::class))
  abstract fun searchAddressActivity(): SearchAddressActivity

}
