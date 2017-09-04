package com.transportation.letsride.feature.search.ui.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

class TextViewTextObservable(
    private val view: TextView
) : InitialValueObservable<CharSequence>() {

  override fun subscribeListener(observer: Observer<in CharSequence>) {
    val listener = Listener(view, observer)
    observer.onSubscribe(listener)
    view.addTextChangedListener(listener)
  }

  override fun getInitialValue(): CharSequence {
    return view.text
  }

  internal class Listener(private val view: TextView, private val observer: Observer<in CharSequence>) : MainThreadDisposable(), TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
      if (!isDisposed) {
        observer.onNext(s)
      }
    }

    override fun afterTextChanged(s: Editable) {}

    override fun onDispose() {
      view.removeTextChangedListener(this)
    }
  }
}
