package com.transportation.letsride.setup

import android.location.Location
import com.google.android.gms.common.api.GoogleApiClient
import com.google.gson.Gson
import com.transportation.letsride.App
import com.transportation.letsride.common.di.component.ApplicationComponent
import com.transportation.letsride.common.di.module.AndroidModule
import com.transportation.letsride.common.di.module.ApiModule
import com.transportation.letsride.common.di.module.FeatureModule
import com.transportation.letsride.common.di.module.GoogleModule
import com.transportation.letsride.common.di.module.LoggerModule
import com.transportation.letsride.common.di.module.NetworkModule
import com.transportation.letsride.common.di.module.ParsersModule
import com.transportation.letsride.common.di.module.RepositoryModule
import com.transportation.letsride.common.di.module.ViewModelModule
import com.transportation.letsride.data.source.LocationDataSource
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        TestAppModule::class,
        FeatureModule::class,
        ViewModelModule::class
    )
)
interface TestApplicationComponent : ApplicationComponent {
  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>()
}

@Module(includes = arrayOf(
    AndroidModule::class,
    LoggerModule::class,
    RepositoryModule::class,
    TestNetworkModule::class,
    TestLocationModule::class,
    ApiModule::class,
    GoogleModule::class,
    ParsersModule::class
))
abstract class TestAppModule

@Module
class TestNetworkModule : NetworkModule() {
  override fun buildRetrofit(baseUrl: String, httpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://localhost/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }
}

@Module
class TestLocationModule {
  @Provides
  @Singleton
  fun providesLocationDataSource(
      googleApiClient: GoogleApiClient
  ): LocationDataSource = object : LocationDataSource {
    override fun getLocationsStream() = Observable.just(
        Location("mock").apply { latitude = 40.4455436; longitude = -3.7086018 /* Cabify HQ */ }
    )
  }
}
