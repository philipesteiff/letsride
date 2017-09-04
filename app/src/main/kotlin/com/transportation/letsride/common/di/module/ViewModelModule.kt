package com.transportation.letsride.common.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.transportation.letsride.feature.pickup.viewmodel.MapViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropOffViewModel
import com.transportation.letsride.feature.search.viewmodel.SearchAddressViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(MapViewModel::class)
  abstract fun bindPickupViewModel(customMapViewModel: MapViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(PickupDropOffViewModel::class)
  abstract fun pickupDropOffViewModel(pickupDropOffViewModel: PickupDropOffViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(SearchAddressViewModel::class)
  abstract fun searchAddressViewModel(searchAddressViewModel: SearchAddressViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: LetsRideViewModelFactory): ViewModelProvider.Factory

}
