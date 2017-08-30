package com.transportation.letsride.common.di.module

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GoogleModule {

  @Provides
  @Singleton
  fun providesGoogleApiClient(context: Context): GoogleApiClient {
    return GoogleApiClient.Builder(context)
        .addApi(LocationServices.API)
        .build()
  }

}
