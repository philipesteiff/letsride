package com.transportation.letsride.common.util

import android.arch.lifecycle.*
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

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
  observe(owner, Observer<T> { v ->
    if (v != null)
      observer.invoke(v)
    else throw IllegalStateException("This observer receives a null. Owner: $owner")
  })
}

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>): LiveData<Y>
    = Transformations.switchMap(this, func)
