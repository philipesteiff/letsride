package com.transportation.letsride.common.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable
import org.reactivestreams.Publisher

enum class Signal { INSTANCE }

fun <T> Publisher<T>.asLiveData() = LiveDataReactiveStreams.fromPublisher(this)

fun <T> LiveData<T>.asPublisher(lifecycleOwner: LifecycleOwner): Flowable<T> {
  return Flowable.fromPublisher(
      LiveDataReactiveStreams.toPublisher(lifecycleOwner, this)
  )
}
