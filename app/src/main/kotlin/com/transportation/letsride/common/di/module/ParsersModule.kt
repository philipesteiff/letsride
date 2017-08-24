package com.transportation.letsride.common.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ParsersModule {

  @Provides
  @Singleton
  fun providesGson(): Gson = GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
      .create()

}
