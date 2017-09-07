package com.transportation.letsride.common.di.module

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.os.Build
import com.transportation.letsride.App
import com.transportation.letsride.data.executor.ApplicationSchedulers
import com.transportation.letsride.data.executor.SchedulerProvider
import dagger.Module
import dagger.Provides
import java.util.Locale
import javax.inject.Singleton

@Module
class AndroidModule {

  @Provides
  @Singleton
  fun providesContext(application: App): Context = application

  @Provides
  @Singleton
  fun providesApplication(application: App): Application = application

  @Provides
  @Singleton
  fun providesDefaultLocale(context: Context): Locale {
    return context.run {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales[0]
      } else {
        resources.configuration.locale
      }
    }
  }

  @Provides
  @Singleton
  fun providesGeocoder(context: Context, locale: Locale): Geocoder {
    return Geocoder(context, locale)
  }

  @Provides
  @Singleton
  fun providesSchedulers(): SchedulerProvider {
    return ApplicationSchedulers()
  }

}
