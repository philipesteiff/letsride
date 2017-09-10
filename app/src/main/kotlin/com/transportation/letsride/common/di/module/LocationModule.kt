package com.transportation.letsride.common.di.module

import com.google.android.gms.common.api.GoogleApiClient
import com.transportation.letsride.data.source.LocationDataSource
import com.transportation.letsride.data.source.LocationDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

  @Provides
  @Singleton
  fun providesLocationDataSource(googleApiClient: GoogleApiClient): LocationDataSource {
    return LocationDataSourceImpl(googleApiClient)
  }

}
