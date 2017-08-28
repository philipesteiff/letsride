package com.transportation.letsride.feature.search.presenter

import android.os.Bundle
import com.transportation.letsride.data.model.AutocompleteOptions
import com.transportation.letsride.data.model.AutocompleteSuggestion
import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.feature.search.SearchAddressContract
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

class SearchAddressPresenter(
    val routeRepository: Repository.Route
) : SearchAddressContract.Presenter {

  lateinit private var view: SearchAddressContract.View

  private var disposable: Disposable = Disposables.empty()

  override fun bindView(view: SearchAddressContract.View) {
    this.view = view
  }

  override fun onViewReady(savedInstanceState: Bundle?, extras: Bundle?) {

    val options = AutocompleteOptions()

    disposable = view.addressChanges()
        .flatMapSingle { input -> routeRepository.query(input, options) }
        .subscribe(
            { predictions -> view.showPredictions(predictions.orEmpty()) },
            { },
            { }
        )
    view.addressChanges()
  }

  override fun onSaveInstanceState(bundle: Bundle?) {

  }

  override fun onViewDestroy() {
    disposable.dispose()
  }

  override fun onClickSuggestion(suggestion: AutocompleteSuggestion) {

  }
}
