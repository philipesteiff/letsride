package com.transportation.letsride.common.di.module

import com.transportation.letsride.data.api.GoogleMapsApi
import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.model.AutoCompleteOptions
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

  @Provides
  @Singleton
  fun providesApi(retrofit: Retrofit): JourneyApi {
    return retrofit.create(JourneyApi::class.java)
  }

  @Provides
  @Singleton
  fun providesGoogleMapsApi(@Named("MapsGoogleApi") retrofit: Retrofit): GoogleMapsApi {
    return retrofit.create(GoogleMapsApi::class.java)
  }

  @Provides
  @Singleton
  fun providesAutocompleteOptions(
      locale: Locale
  ): AutoCompleteOptions {
    return AutoCompleteOptions(
        language = locale.language,
        radius = TEN_KM_IN_METERS,
        components = listOf(DEFAULT_COUNTRY)

    )
  }

  companion object {
    private const val DEFAULT_COUNTRY = "country:br"
    private const val TEN_KM_IN_METERS = 10000
  }

}
