package com.transportation.letsride.common.di.component

import com.transportation.letsride.App
import com.transportation.letsride.common.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(AppModule::class)
)
interface ApplicationComponent : AndroidInjector<App> {

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>()

}
