package com.transportation.letsride.common.di.module

import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

@Module
class LoggerModule {

  @Provides
  @Singleton
  fun provideTimberTree(): Timber.Tree = Timber.DebugTree()

}
