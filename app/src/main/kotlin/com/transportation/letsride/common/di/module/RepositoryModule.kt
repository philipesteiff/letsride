package com.transportation.letsride.common.di.module

import com.transportation.letsride.data.api.GoogleMapsApi
import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.repository.CategoryRepository
import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.data.repository.RouteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Provides
  @Singleton
  fun providesCategoryRepository(
      journeyApi: JourneyApi
  ): Repository.Category {
    return CategoryRepository(journeyApi)
  }

  @Provides
  @Singleton
  fun providesRouteRepository(
      schedulerProvider: SchedulerProvider,
      mapsApi: GoogleMapsApi
  ): Repository.Route {
    return RouteRepository(
        schedulerProvider,
        mapsApi
    )
  }

}
