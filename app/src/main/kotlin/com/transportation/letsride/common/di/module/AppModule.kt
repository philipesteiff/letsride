package com.transportation.letsride.common.di.module

import dagger.Module

@Module(includes = arrayOf(
    AndroidModule::class,
    LoggerModule::class,
    RepositoryModule::class,
    NetworkModule::class,
    ApiModule::class,
    LocationModule::class,
    GoogleModule::class,
    ParsersModule::class
))
abstract class AppModule
