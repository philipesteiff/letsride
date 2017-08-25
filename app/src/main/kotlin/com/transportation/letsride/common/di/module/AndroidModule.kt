package com.transportation.letsride.common.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import java.util.Locale
import javax.inject.Singleton

@Module
class AndroidModule(
    private val application: Application
) {

  @Provides
  @Singleton
  fun provideApplicationContext(): Context = application

  @Provides
  @Singleton
  fun providesDefaultLocale(context: Context): Locale {
    return context.resources.configuration.locale
  }

}
