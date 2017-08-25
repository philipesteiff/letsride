package com.transportation.letsride.common.di.module

import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.repository.CategoryRepository
import com.transportation.letsride.data.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

  @Provides
  @Singleton
  fun providesCategoryRepository(
      journeyApi: JourneyApi
  ): Repository.Category {
    return CategoryRepository(journeyApi)
  }

}
