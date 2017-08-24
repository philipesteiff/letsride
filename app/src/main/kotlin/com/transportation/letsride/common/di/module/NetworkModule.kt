package com.transportation.letsride.common.di.module

import android.support.annotation.VisibleForTesting
import com.google.gson.Gson
import com.transportation.letsride.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@VisibleForTesting
open class NetworkModule {

  //region HttpClient

  @Provides
  @Singleton
  fun providesLoggingInterceptor(): HttpLoggingInterceptor {
    return when {
      BuildConfig.DEBUG -> HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
      else -> HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
  }

  @Provides
  @Singleton
  fun providesHttpClient(
      loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
  }

  //endregion

  //region Retrofit

  @VisibleForTesting
  protected open fun buildRetrofit(
      baseUrl: String,
      httpClient: OkHttpClient,
      gson: Gson
  ): Retrofit = Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(httpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()

  @Provides
  @Singleton
  @Named("api")
  fun provideLegacyApiBuilder(httpClient: OkHttpClient, gson: Gson): Retrofit {
    return buildRetrofit(BuildConfig.API_HOST, httpClient, gson)
  }

  //endregion

}

