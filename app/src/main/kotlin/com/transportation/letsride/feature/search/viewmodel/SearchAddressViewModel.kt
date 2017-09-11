package com.transportation.letsride.feature.search.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.transportation.letsride.common.extensions.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.repository.AddressRepository
import timber.log.Timber
import javax.inject.Inject

class SearchAddressViewModel @Inject constructor(
    private val schedulers: SchedulerProvider,
    private val addressRepository: AddressRepository
) : BaseViewModel() {

  val inputAddress = MutableLiveData<String>()
  val searchResults = MediatorLiveData<List<PinPoint>>().apply {
    addSource(inputAddress) { query(it) }
  }

  val selectedPrediction = MutableLiveData<PinPoint>()
  val selectedAddress = MediatorLiveData<PinPoint?>().apply {
    addSource(selectedPrediction) { retrieveAddress(it) }
  }

  fun onInputAddressChange(input: String?) {
    if (!input.isNullOrBlank())
      inputAddress.value = input
  }

  fun onPredictionSelected(prediction: PinPoint) {
    selectedPrediction.value = prediction
  }

  private fun query(inputAddress: String?) {
    disposables += addressRepository.query(inputAddress.orEmpty())
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribe(
            { predictions -> searchResults.value = predictions },
            { error -> Timber.e(error) }
        )
  }

  private fun retrieveAddress(prediction: PinPoint?) {
    prediction?.let { selectedAddress.value = prediction }
  }

}
