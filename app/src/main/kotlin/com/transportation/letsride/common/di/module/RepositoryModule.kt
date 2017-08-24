package com.transportation.letsride.common.di.module

import com.transportation.letsride.data.repository.CategoryRepository
import com.transportation.letsride.data.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepositoryModule {

  @Provides
  @Singleton
  fun providesUserRepository(

  ): Repository.Category {
    return CategoryRepository()
  }

}
