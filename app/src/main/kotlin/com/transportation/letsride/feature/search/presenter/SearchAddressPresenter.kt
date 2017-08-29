package com.transportation.letsride.feature.search.presenter

import android.os.Bundle
import com.transportation.letsride.data.model.AutocompleteOptions
import com.transportation.letsride.data.model.AutocompleteSuggestion
import com.transportation.letsride.data.model.Prediction
import com.transportation.letsride.data.repository.Repository
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables

class SearchAddressPresenter(
    val autocompleteOptions: AutocompleteOptions,
    val routeRepository: Repository.Route
)  {


  private var disposable: Disposable = Disposables.empty()
  private var predictions: List<Prediction> = emptyList()


   fun onViewReady(savedInstanceState: Bundle?, extras: Bundle?) {
    listenAddressChanges()

    if (savedInstanceState != null) {
//      predictions = savedInstanceState.getParcelableArrayList(SearchAddressContract.Presenter.STATE_PREDICTIONS)
      onPredictionsReceived(predictions)
    }
  }

   fun onSaveInstanceState(bundle: Bundle?) {
//    bundle?.putParcelableArrayList(SearchAddressContract.Presenter.STATE_PREDICTIONS, ArrayList(predictions))
  }

   fun onViewDestroy() {
    disposable.dispose()
  }

  private fun listenAddressChanges() {
//    disposable = view.addressChanges()
//        .flatMapSingle { input -> routeRepository.query(input, autocompleteOptions) }
//        .subscribe(
//            { predictions -> onPredictionsReceived(predictions) },
//            { error -> Timber.e(error) /* TODO Show error view */ },
//            { /* TODO Hide loading view */ }
//        )
  }

  private fun onPredictionsReceived(predictions: List<Prediction>) {
    this.predictions = predictions
//    view.showPredictions(predictions)
  }

   fun onClickSuggestion(suggestion: AutocompleteSuggestion) {
//    view.finishViewWith(suggestion)
  }
}
