package com.transportation.letsride.common.di.module

import android.support.annotation.VisibleForTesting
import com.google.gson.Gson
import com.transportation.letsride.BuildConfig
import com.transportation.letsride.data.network.interceptor.AuthHeaders
import com.transportation.letsride.data.network.interceptor.HeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@VisibleForTesting
open class NetworkModule {

  //region Interceptors

  @Provides
  @Singleton
  internal fun provideAuthHeaders(): AuthHeaders {
    return AuthHeaders()
  }

  @Provides
  @VisibleForTesting
  open fun providesHeaderInterceptor(
      authHeaders: AuthHeaders,
      locale: Locale
  ): HeaderInterceptor {
    return HeaderInterceptor(authHeaders, locale)
  }

  //endregion

  //region HttpClient

  @Provides
  @Singleton
  fun providesLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      level = when {
        BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
      }
    }
  }

  @Provides
  @Singleton
  fun providesHttpClient(
      headerInterceptor: HeaderInterceptor,
      loggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()
  }

  //endregion

  //region Retrofit

  @Provides
  @Singleton
  fun provideApiBuilder(httpClient: OkHttpClient, gson: Gson): Retrofit {
    return buildRetrofit(BuildConfig.API_HOST, httpClient, gson)
  }

  @Provides
  @Singleton
  @Named("MapsGoogleApi")
  fun provideGoogleMapsApiBuilder(httpClient: OkHttpClient, gson: Gson): Retrofit {
    return buildRetrofit(BuildConfig.API_GOOGLE_MAPS, httpClient, gson)
  }

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

  //endregion

}

