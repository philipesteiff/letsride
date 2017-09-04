package com.transportation.letsride.feature.search.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.common.util.observe
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.model.Prediction
import com.transportation.letsride.feature.search.ui.adapter.SearchAddressAdapter
import com.transportation.letsride.feature.search.ui.widget.TextViewTextObservable
import com.transportation.letsride.feature.search.viewmodel.SearchAddressViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_address.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchAddressActivity : BaseActivity() {

  val viewModel: SearchAddressViewModel by unsafeLazy {
    ViewModelProviders.of(this, viewModelFactory)
        .get(SearchAddressViewModel::class.java)
  }

  val disposables = CompositeDisposable()

  val adapter by unsafeLazy {
    SearchAddressAdapter().apply {
      onClick = { viewModel.onPredictionSelected(it as Prediction) }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search_address)
    recyclerSearchAddress.adapter = adapter

    listenData()
    listenViews()
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }

  fun listenData() {

    viewModel.selectedAddress
        .observe(this, this::onAddressSelected)

    viewModel.searchResults
        .observe(this, this::onReceiveSearchResults)
  }

  fun listenViews() {
    disposables += addressChanges().subscribe(
        { input -> viewModel.onInputAddressChange(input) },
        { error -> Timber.e(error) }
    )
  }

  private fun onReceiveSearchResults(results: List<Prediction>?) {
    adapter.clear()
    adapter.addAll(results.orEmpty())
  }

  private fun onAddressSelected(address: Address?) {
    if (address != null)
      finishWith(address)
    else Timber.e("Selected address was null")
  }

  fun addressChanges(): Observable<String> {
    return TextViewTextObservable(editSearchAddress)
        .map(CharSequence::toString)
        .filter { it.length > 2 }
        .debounce(100, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
  }


  private fun finishWith(address: Address) {
    val intent = Intent()
        .putExtra(EXTRA_ADDRESS, address)
    setResult(RESULT_OK, intent).also { finish() }
  }

  companion object {
    const val EXTRA_ADDRESS = "extra_address"

    fun getIntent(context: Context) = Intent(context, SearchAddressActivity::class.java)
  }

}
