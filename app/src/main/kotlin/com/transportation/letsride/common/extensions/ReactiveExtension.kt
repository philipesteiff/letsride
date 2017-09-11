package com.transportation.letsride.common.extensions

import android.arch.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
  add(disposable)
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
  observe(owner, Observer<T> { v ->
      observer.invoke(v)
  })
}

