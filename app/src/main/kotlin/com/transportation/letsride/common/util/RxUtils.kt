package com.transportation.letsride.common.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import org.reactivestreams.Publisher

enum class Signal { INSTANCE }

fun <T> Publisher<T>.asLiveData() = LiveDataReactiveStreams.fromPublisher(this)

fun <T> LiveData<T>.asPublisher(lifecycleOwner: LifecycleOwner): Publisher<T> {
  return LiveDataReactiveStreams.toPublisher(lifecycleOwner, this)
}
