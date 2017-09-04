package com.transportation.letsride.common.di.module

import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.repository.*
import com.transportation.letsride.data.source.AddressDataSource
import com.transportation.letsride.data.source.LocationDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

  @Provides
  @Singleton
  fun providesLocationRepository(
      locationDataSource: LocationDataSource
  ): LocationRepository {
    return LocationRepositoryImpl(
        locationDataSource
    )
  }

  @Provides
  @Singleton
  fun providesJourneyRepository(
      journeyApi: JourneyApi
  ): JourneyRepository {
    return JourneyRepositoryImpl(journeyApi)
  }

  @Provides
  @Singleton
  fun providesAddressRepository(
      addressDataSource: AddressDataSource
  ): AddressRepository {
    return AddressRepositoryImpl(addressDataSource)
  }

}
