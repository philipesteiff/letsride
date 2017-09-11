package com.transportation.letsride.data.network.interceptor

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class HeaderInterceptor(
    private val authHeaders: AuthHeaders,
    private val locale: Locale
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response? {
    val newRequest = chain.request().newBuilder()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      newRequest.addHeader("Accept-Language", locale.toLanguageTag())
    } else {
      newRequest.addHeader("Accept-Language", locale.isO3Language)
    }

    authHeaders.headers.forEach { entry ->
      newRequest.addHeader(entry.key, entry.value)
    }

    return chain.proceed(newRequest.build())
  }
}
