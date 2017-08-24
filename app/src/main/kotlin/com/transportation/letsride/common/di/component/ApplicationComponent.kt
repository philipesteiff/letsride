package com.transportation.letsride.common.di.component

import android.content.Context
import com.transportation.letsride.App
import com.transportation.letsride.common.di.module.AndroidModule
import com.transportation.letsride.common.di.module.ApiModule
import com.transportation.letsride.common.di.module.LoggerModule
import com.transportation.letsride.common.di.module.NetworkModule
import com.transportation.letsride.common.di.module.ParsersModule
import com.transportation.letsride.common.di.module.RepositoryModule
import com.transportation.letsride.data.repository.Repository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidModule::class,
        LoggerModule::class,
        RepositoryModule::class,
        NetworkModule::class,
        ApiModule::class,
        ParsersModule::class
    )
)
interface ApplicationComponent {

  fun inject(app: App)

  // Android
  fun context(): Context

  //region Repositories
  fun categoryRepository(): Repository.Category
  //endregion
}
