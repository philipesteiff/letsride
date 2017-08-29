package com.transportation.letsride.common.di.module

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module


@Module()
abstract class ViewModelModule {

//  @Binds
//  @IntoMap
//  @ViewModelKey(SignInViewModel::class)
//  abstract fun bindSignInViewModel(signInViewModel: SignInViewModel): ViewModel
//
//  @Binds
//  @IntoMap
//  @ViewModelKey(HomeViewModel::class)
//  abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
//
//  @Binds
//  @IntoMap
//  @ViewModelKey(WeekViewModel::class)
//  abstract fun bindWeekViewModel(weekViewModel: WeekViewModel): ViewModel
//
//  @Binds
//  @IntoMap
//  @ViewModelKey(ReportsViewModel::class)
//  abstract fun bindReportsViewModel(reportsViewModel: ReportsViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(
      factory: LetsRideViewModelFactory
  ): ViewModelProvider.Factory
}
