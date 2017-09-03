package com.transportation.letsride.feature.pickupdropoff.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.navigation.Navigator
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.repository.Repository
import timber.log.Timber
import javax.inject.Inject

class PickupDropOffViewModel @Inject constructor(
    private val locationRepository: Repository.Location,
    private val schedulers: SchedulerProvider
) : BaseViewModel() {

  val newMapLocation = MutableLiveData<LatLng>()

  val pickupAddressChange = MediatorLiveData<Address?>().apply {
    addSource(newMapLocation) { latLng ->
      latLng?.run { findAddress(latLng) } ?: Timber.e("latlng was null")
    }
  }

  val dropOffAddressChange = MutableLiveData<Address?>()

  val navigateToPickupAddressSearch = MutableLiveData<Address?>()
  val navigateToDropOffAddressSearch = MutableLiveData<Address?>()

  private fun findAddress(latLng: LatLng) {
    disposables += locationRepository
        .getAddressFromLocation(latLng)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribe(
            { address -> pickupAddressChange.value = address },
            { error -> Timber.e(error) }
        )
  }

  fun newCurrentLocation(latLng: LatLng) {
    newMapLocation.value = latLng
  }

  fun onPickupAddressClicked() {
    navigateToPickupAddressSearch.value = pickupAddressChange.value
  }

  fun onDropoffAddressClicked() {
    navigateToDropOffAddressSearch.value = dropOffAddressChange.value
  }

  fun onReceivePickupAddressResult(address: Address) {
    pickupAddressChange.value = address
  }

  fun onReveiveDropOffAddressResult(address: Address) {
    dropOffAddressChange.value = address
  }

}
