package com.transportation.letsride.common.di.module

import com.transportation.letsride.data.repository.AddressRepository
import com.transportation.letsride.data.repository.AddressRepositoryImpl
import com.transportation.letsride.data.repository.JourneyRepository
import com.transportation.letsride.data.repository.JourneyRepositoryImpl
import com.transportation.letsride.data.repository.LocationRepository
import com.transportation.letsride.data.repository.LocationRepositoryImpl
import com.transportation.letsride.data.source.AddressDataSource
import com.transportation.letsride.data.source.EstimatesDataSource
import com.transportation.letsride.data.source.LocationDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(
    AndroidModule::class,
    LoggerModule::class,
    NetworkModule::class,
    ApiModule::class,
    LocationModule::class,
    GoogleModule::class,
    ParsersModule::class
))
class AppModule {

  @Provides
  @Singleton
  open fun providesLocationRepository(
      locationDataSource: LocationDataSource
  ): LocationRepository {
    return LocationRepositoryImpl(locationDataSource)
  }

  @Provides
  @Singleton
  fun providesJourneyRepository(
      estimatesDataSource: EstimatesDataSource
  ): JourneyRepository {
    return JourneyRepositoryImpl(estimatesDataSource)
  }

  @Provides
  @Singleton
  fun providesAddressRepository(
      addressDataSource: AddressDataSource
  ): AddressRepository {
    return AddressRepositoryImpl(addressDataSource)
  }

}
