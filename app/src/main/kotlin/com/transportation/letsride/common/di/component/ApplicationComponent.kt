package com.transportation.letsride.common.di.component

import com.transportation.letsride.App
import com.transportation.letsride.common.di.module.AppModule
import com.transportation.letsride.common.di.module.FeatureModule
import com.transportation.letsride.common.di.module.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        AppModule::class,
        FeatureModule::class,
        ViewModelModule::class
    )
)
interface ApplicationComponent : AndroidInjector<App> {

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>()

}
