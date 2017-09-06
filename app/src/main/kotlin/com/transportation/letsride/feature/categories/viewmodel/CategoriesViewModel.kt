package com.transportation.letsride.feature.categories.viewmodel

import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.repository.JourneyRepository
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses
import timber.log.Timber
import javax.inject.Inject

class CategoriesViewModel @Inject constructor(
    private val schedulers: SchedulerProvider,
    private val journeyRepository: JourneyRepository
) : BaseViewModel() {

  fun loadCategories(filledAddresses: FilledAddresses?) {
    filledAddresses?.let { (pickupAddress, dropOffAddress) ->
      disposables += journeyRepository
          .estimates(pickupAddress, dropOffAddress)
          .subscribeOn(schedulers.io())
          .observeOn(schedulers.ui())
          .subscribe(
              { estimates -> Timber.d(estimates.toString()) },
              { error -> Timber.e(error) }
          )
    }
  }


}
