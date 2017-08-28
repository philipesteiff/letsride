package com.transportation.letsride.feature.route.di

import com.transportation.letsride.common.di.scopes.PerFragment
import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.feature.route.RouteContract
import com.transportation.letsride.feature.route.presenter.RoutePresenter
import dagger.Module
import dagger.Provides

@Module
class RouteFragmentModule {

  @Provides
  @PerFragment
  fun provideRoutePresenter(
      routerRepository: Repository.Route
  ): RouteContract.Presenter {
    return RoutePresenter(routerRepository)
  }
}
