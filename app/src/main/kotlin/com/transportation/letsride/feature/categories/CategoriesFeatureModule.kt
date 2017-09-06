package com.transportation.letsride.feature.categories

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.categories.ui.fragment.CategoriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoriesFeatureModule {

  @PerFragment
  @ContributesAndroidInjector
  abstract fun categoriesFragmentInjector(): CategoriesFragment

}
