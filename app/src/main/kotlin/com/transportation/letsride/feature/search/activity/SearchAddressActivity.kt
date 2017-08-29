package com.transportation.letsride.feature.search.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.RxTextView
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.data.model.AutocompleteSuggestion
import com.transportation.letsride.data.model.Prediction
import com.transportation.letsride.feature.search.SearchAddressContract
import com.transportation.letsride.feature.search.adapter.SearchAddressAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_address.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchAddressActivity : BaseActivity(), SearchAddressContract.View {

  @Inject
  lateinit var presenter: SearchAddressContract.Presenter

  val adapter by lazy { SearchAddressAdapter().apply { onClick = presenter::onClickSuggestion } }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_address)

    recyclerSearchAddress.adapter = adapter

    presenter.bindView(this)
    presenter.onViewReady(savedInstanceState, intent.extras)
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    presenter.onSaveInstanceState(outState)
    super.onSaveInstanceState(outState)
  }

  override fun onDestroy() {
    presenter.onViewDestroy()
    super.onDestroy()
  }

  override fun addressChanges(): Observable<String> {
    return RxTextView.textChanges(editSearchAddress)
        .map(CharSequence::toString)
        .filter { it.length > 2 }
        .debounce(100, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }

  override fun showPredictions(predictions: List<Prediction>) {
    adapter.clear()
    adapter.addAll(predictions)
  }

  override fun finishViewWith(suggestion: AutocompleteSuggestion) {
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
