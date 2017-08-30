package com.transportation.letsride.common.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

  val disposables = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }
}
