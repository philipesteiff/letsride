package com.transportation.letsride.feature.search.di

import com.transportation.letsride.common.di.scopes.PerActivity
import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.feature.search.SearchAddressContract
import com.transportation.letsride.feature.search.presenter.SearchAddressPresenter
import dagger.Module
import dagger.Provides

@Module
class SearchAddressActivityModule {

  @Provides
  @PerActivity
  fun provideSearchAddressPresenter(
      routeRepository: Repository.Route
  ): SearchAddressContract.Presenter {
    return SearchAddressPresenter(routeRepository)
  }

}
