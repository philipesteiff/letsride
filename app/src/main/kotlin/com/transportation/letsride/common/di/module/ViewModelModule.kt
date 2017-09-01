package com.transportation.letsride.common.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.transportation.letsride.feature.map.viewmodel.CustomMapViewModel
import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropoffViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(PickupViewModel::class)
  abstract fun bindPickupViewModel(customMapViewModel: PickupViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(CustomMapViewModel::class)
  abstract fun bindLocationViewModel(customMapViewModel: CustomMapViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(PickupDropoffViewModel::class)
  abstract fun pickupDropoffViewModel(pickupDropoffViewModel: PickupDropoffViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: LetsRideViewModelFactory): ViewModelProvider.Factory

}
