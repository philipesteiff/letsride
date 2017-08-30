package com.transportation.letsride.common.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Publisher

enum class Signal { INSTANCE }

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
  add(disposable)
}

fun <T> Publisher<T>.asLiveData() = LiveDataReactiveStreams.fromPublisher(this)

fun <T> LiveData<T>.asPublisher(lifecycleOwner: LifecycleOwner): Flowable<T> {
  return Flowable.fromPublisher(
      LiveDataReactiveStreams.toPublisher(lifecycleOwner, this)
  )
}
