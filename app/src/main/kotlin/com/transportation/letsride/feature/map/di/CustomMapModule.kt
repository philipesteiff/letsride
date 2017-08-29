package com.transportation.letsride.feature.map.di

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.feature.map.MapControlsContract
import com.transportation.letsride.feature.map.presenter.MapControlsPresenter
import dagger.Module
import dagger.Provides

@Module
class CustomMapModule {

  @Provides
  @PerFragment
  fun provideMapControlsPresenter(
  ): MapControlsContract.Presenter {
    return MapControlsPresenter()
  }

}

