package com.transportation.letsride.feature.search.ui.widget

import io.reactivex.Observable
import io.reactivex.Observer

abstract class InitialValueObservable<T> : Observable<T>() {
  override fun subscribeActual(observer: Observer<in T>) {
    subscribeListener(observer)
    observer.onNext(getInitialValue())
  }

  protected abstract fun subscribeListener(observer: Observer<in T>)
  protected abstract fun getInitialValue(): T


  fun skipInitialValue(): Observable<T> {
    return Skipped()
  }

  private inner class Skipped internal constructor() : Observable<T>() {

    override fun subscribeActual(observer: Observer<in T>) {
      subscribeListener(observer)
    }
  }
}
