package com.transportation.letsride.feature.search

import com.transportation.letsride.common.di.scopes.PerActivity
import com.transportation.letsride.feature.search.activity.SearchAddressActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchAddressFeatureModule {

  @PerActivity
  @ContributesAndroidInjector
  abstract fun searchAddressActivity(): SearchAddressActivity

}
