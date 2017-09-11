package com.transportation.letsride.feature.estimate.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.transportation.letsride.common.extensions.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Estimate
import com.transportation.letsride.data.repository.JourneyRepository
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses
import timber.log.Timber
import javax.inject.Inject

class EstimatesViewModel @Inject constructor(
    private val schedulers: SchedulerProvider,
    private val journeyRepository: JourneyRepository
) : BaseViewModel() {

  val estimates = MutableLiveData<List<Estimate>>()

  fun loadEstimates(filledAddresses: FilledAddresses?) {
    filledAddresses?.let { (pickupAddress, dropOffAddress) ->
      disposables += journeyRepository
          .estimates(pickupAddress, dropOffAddress)
          .subscribeOn(schedulers.io())
          .observeOn(schedulers.ui())
          .subscribe(
              { estimatesResult -> estimates.value = estimatesResult },
              { error -> Timber.e(error) }
          )
    }
  }


}
