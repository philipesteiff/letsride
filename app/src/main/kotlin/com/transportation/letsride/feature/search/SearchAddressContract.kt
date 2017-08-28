package com.transportation.letsride.feature.search

import com.transportation.letsride.common.Presentable
import com.transportation.letsride.data.model.AutocompleteSuggestion
import com.transportation.letsride.data.model.Prediction
import io.reactivex.Observable


object SearchAddressContract {

  interface Presenter : Presentable<SearchAddressContract.View> {
    fun onViewDestroy()
    fun onClickSuggestion(suggestion: AutocompleteSuggestion)
  }

  interface View {
    fun addressChanges(): Observable<String>
    fun showPredictions(predictions: List<Prediction>)
  }

}
