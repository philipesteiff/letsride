package com.transportation.letsride.feature.pickup.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses
import javax.inject.Inject

enum class ViewState { INIT, ESTIMATE }

class PickupViewModel @Inject constructor() : BaseViewModel() {

  val viewState = MutableLiveData<ViewState>().apply { value = ViewState.INIT }

  val isEstimatesVisible = MediatorLiveData<Boolean>().apply {
    addSource(viewState) { viewState ->
      when (viewState) {
        ViewState.INIT -> value = false
        ViewState.ESTIMATE -> value = true
      }
    }
  }

  val estimates = MutableLiveData<FilledAddresses>()

  fun pickupDropOffAddressFilled(filledAddresses: FilledAddresses?) {
    filledAddresses?.let { addresses ->
      viewState.value = ViewState.ESTIMATE
      estimates.value = addresses
    }
  }

  fun onBackPressed(): Boolean {
    return when (viewState.value) {
      ViewState.INIT -> true
      else -> {
        viewState.value = ViewState.INIT
        false
      }
    }
  }

}
