package com.transportation.letsride.common.viewmodel

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.os.Parcelable
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

  val disposables = CompositeDisposable()

  var shouldCheckSavedState = false

  init {
    shouldCheckSavedState = true
  }

  fun savedState(savedInstanceState: Bundle?) {
    if (isApplicationKilled(savedInstanceState)) {
      savedInstanceState?.let { restoreState(it) }
    }
    shouldCheckSavedState = false
  }

  protected open fun restoreState(savedInstanceState: Bundle) {}

  open fun saveState(): Parcelable? {
    return null
  }

  private fun isApplicationKilled(savedInstanceState: Bundle?) = shouldCheckSavedState && savedInstanceState != null

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }
}
