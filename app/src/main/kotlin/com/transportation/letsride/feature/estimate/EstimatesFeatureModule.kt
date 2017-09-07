package com.transportation.letsride.feature.estimate

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.estimate.ui.fragment.EstimatesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EstimatesFeatureModule {

  @PerFragment
  @ContributesAndroidInjector
  abstract fun estimatesFragmentInjector(): EstimatesFragment

}
