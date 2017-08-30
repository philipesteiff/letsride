package com.transportation.letsride.common.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.transportation.letsride.feature.location.LocationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(LocationViewModel::class)
  abstract fun bindLocationViewModel(locationViewModel: LocationViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: LetsRideViewModelFactory): ViewModelProvider.Factory
}
