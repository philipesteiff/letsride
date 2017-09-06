package com.transportation.letsride.common.di.module

import com.transportation.letsride.data.api.JourneyApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

  @Provides
  @Singleton
  fun providesApi(retrofit: Retrofit): JourneyApi {
    return retrofit.create(JourneyApi::class.java)
  }

}
