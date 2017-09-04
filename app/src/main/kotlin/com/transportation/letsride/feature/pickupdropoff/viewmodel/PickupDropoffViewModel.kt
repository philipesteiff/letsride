package com.transportation.letsride.feature.pickupdropoff.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.repository.AddressRepository
import timber.log.Timber
import javax.inject.Inject

class PickupDropOffViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val schedulers: SchedulerProvider
) : BaseViewModel() {

  val newMapLocation = MutableLiveData<LatLng>()

  val pickupAddressChange = MediatorLiveData<Address?>().apply {
    addSource(newMapLocation) { latLng ->
      latLng?.run { findAddress(latLng) } ?: Timber.e("latlng was null")
    }
  }

  val dropOffAddressChange = MutableLiveData<Address?>()


  val navigateToPickupAddressSearch = MutableLiveData<Unit>()
  val navigateToDropOffAddressSearch = MutableLiveData<Unit>()

  private fun findAddress(latLng: LatLng) {
    disposables += addressRepository
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
    navigateToPickupAddressSearch.value = Unit
  }

  fun onDropoffAddressClicked() {
    navigateToDropOffAddressSearch.value = Unit
  }

  fun onReceivePickupAddressResult(address: Address) {
    pickupAddressChange.value = address.
  }

  fun onReveiveDropOffAddressResult(address: Address) {
    dropOffAddressChange.value = address
  }

}
