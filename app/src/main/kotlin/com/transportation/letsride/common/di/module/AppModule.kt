package com.transportation.letsride.common.di.module

import dagger.Module

@Module(includes = arrayOf(
    ViewModelModule::class,
    AndroidModule::class,
    LoggerModule::class,
    RepositoryModule::class,
    NetworkModule::class,
    ApiModule::class,
    ParsersModule::class
))
abstract class AppModule
