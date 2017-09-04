package com.transportation.letsride.feature.search.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.model.AutoCompleteOptions
import com.transportation.letsride.data.model.AutoCompleteSuggestion
import com.transportation.letsride.data.model.Prediction
import com.transportation.letsride.data.repository.AddressRepository
import timber.log.Timber
import javax.inject.Inject

class SearchAddressViewModel @Inject constructor(
    private val schedulers: SchedulerProvider,
    private val autoCompleteOptions: AutoCompleteOptions,
    private val addressRepository: AddressRepository
) : BaseViewModel() {

  val inputAddress = MutableLiveData<String>()
  val searchResults = MediatorLiveData<List<Prediction>>().apply {
    addSource(inputAddress) { query(it) }
  }

  val selectedPrediction = MutableLiveData<Prediction>()
  val selectedAddress = MediatorLiveData<Address?>().apply {
    addSource(selectedPrediction) { retrieveAddress(it) }
  }

  fun onInputAddressChange(input: String?) {
    if (!input.isNullOrBlank())
      inputAddress.value = input
  }

  fun onPredictionSelected(prediction: Prediction) {
    selectedPrediction.value = prediction
  }

  private fun query(inputAddress: String?) {
    disposables += addressRepository.query(inputAddress.orEmpty(), autoCompleteOptions)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribe(
            { predictions -> searchResults.value = predictions },
            { error -> Timber.e(error) }
        )
  }

  private fun retrieveAddress(prediction: Prediction?) {
    prediction?.let {
      disposables += addressRepository.reverseGeocode(it.placeId)
          .subscribe(
              { address -> selectedAddress.value = address },
              { error -> Timber.e(error) }
          )
    }
  }

}
