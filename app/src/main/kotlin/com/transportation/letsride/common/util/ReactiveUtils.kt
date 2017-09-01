package com.transportation.letsride.common.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
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

//fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(
//    owner,
//    Observer<T> { v ->
//      observer.invoke(v)
//    }
//)

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>): LiveData<Y>
    = Transformations.switchMap(this, func)