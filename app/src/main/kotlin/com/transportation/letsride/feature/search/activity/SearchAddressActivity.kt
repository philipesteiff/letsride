package com.transportation.letsride.feature.search.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.RxTextView
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.data.model.AutocompleteSuggestion
import com.transportation.letsride.data.model.Prediction
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_address.editSearchAddress
import java.util.concurrent.TimeUnit

class SearchAddressActivity : BaseActivity() {

//  val adapter by unsafeLazy { SearchAddressAdapter().apply { onClick = presenter::onClickSuggestion } }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_address)

//    recyclerSearchAddress.adapter = adapter

//    presenter.bindView(this)
//    presenter.onViewReady(savedInstanceState, intent.extras)
  }

  override fun onSaveInstanceState(outState: Bundle?) {
//    presenter.onSaveInstanceState(outState)
    super.onSaveInstanceState(outState)
  }

  override fun onDestroy() {
//    presenter.onViewDestroy()
    super.onDestroy()
  }

  fun addressChanges(): Observable<String> {
    return RxTextView.textChanges(editSearchAddress)
        .map(CharSequence::toString)
        .filter { it.length > 2 }
        .debounce(100, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }

  fun showPredictions(predictions: List<Prediction>) {
//    adapter.clear()
//    adapter.addAll(predictions)
  }

  fun finishViewWith(suggestion: AutocompleteSuggestion) {
//    val data = Intent()
//    data.putExtra(EXTRA_ADDRESS, suggestion)
//
//    setResult(Activity.RESULT_OK, data)
//    finish()
  }

  companion object {
    const val EXTRA_ADDRESS = "extra_address"

    fun getIntentWithAddress(context: Context, address: String?) = Intent(context, SearchAddressActivity::class.java)
        .apply { putExtra(SearchAddressActivity.EXTRA_ADDRESS, address) }
  }

}
