package com.transportation.letsride.common.di.component

import com.transportation.letsride.App
import com.transportation.letsride.common.di.module.AppModule
import com.transportation.letsride.common.di.module.FeatureModule
import com.transportation.letsride.common.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
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
interface ApplicationComponent {

  @Component.Builder
  interface Builder {

    fun appModule(appModule: AppModule):Builder

    @BindsInstance
    fun application(app: App): Builder

    fun build(): ApplicationComponent
  }

  fun inject(app: App)

}
