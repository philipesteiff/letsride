package com.transportation.letsride.feature.pickupdropoff.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.repository.Repository
import timber.log.Timber
import javax.inject.Inject

class PickupDropoffViewModel @Inject constructor(
    private val locationRepository: Repository.Location,
    private val schedulers: SchedulerProvider
) : BaseViewModel() {

  val newMapLocation = MutableLiveData<LatLng>()

  val addressChange = MediatorLiveData<Address?>().apply {
    addSource(newMapLocation) { latLng ->
      latLng?.run { findAddress(latLng) } ?: Timber.e("latlng was null")
    }
  }

  private fun findAddress(latLng: LatLng) {
    disposables += locationRepository
        .getAddressFromLocation(latLng)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribe(
            { address -> addressChange.value = address },
            { error -> Timber.e(error) }
        )
  }

  fun newCurrentLocation(latLng: LatLng) {
    newMapLocation.value = latLng
  }


}
