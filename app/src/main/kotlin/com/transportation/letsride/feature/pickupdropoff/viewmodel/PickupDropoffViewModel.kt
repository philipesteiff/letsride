package com.transportation.letsride.feature.pickupdropoff.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.repository.AddressRepository
import com.transportation.letsride.feature.pickup.viewmodel.MapCameraPositionAction
import timber.log.Timber
import javax.inject.Inject

typealias FilledAddresses = Pair<Address?, Address?>

class PickupDropOffViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val schedulers: SchedulerProvider
) : BaseViewModel() {

  val pickupAddressChange = MutableLiveData<Address?>()
  val dropOffAddressChange = MutableLiveData<Address?>()

  val adjustMapByPickupAddressLocation = MediatorLiveData<LatLng>().apply {
    addSource(pickupAddressChange) { address -> value = address?.getLatLng() }
  }

  val pickupDropOffAddressFilled = MediatorLiveData<FilledAddresses>().apply {
    addSource(dropOffAddressChange) { dropOffAddress ->
      value = pickupAddressChange.value to dropOffAddress
    }
  }

  val navigateToPickupAddressSearch = MutableLiveData<Unit>()
  val navigateToDropOffAddressSearch = MutableLiveData<Unit>()

  fun newMapCameraPosition(mapCameraPositionAction: MapCameraPositionAction?) {
    when (mapCameraPositionAction) {
      is MapCameraPositionAction.MapDragged -> findAddress(mapCameraPositionAction.newLocation)
      is MapCameraPositionAction.JustMoveMap -> findAddress(mapCameraPositionAction.newLocation)
    }
  }

  fun onPickupAddressClicked() {
    navigateToPickupAddressSearch.value = Unit
  }

  fun onDropoffAddressClicked() {
    navigateToDropOffAddressSearch.value = Unit
  }

  fun onReceivePickupAddressResult(address: Address) {
    pickupAddressChange.value = address
  }

  fun onReceiveDropOffAddressResult(address: Address) {
    dropOffAddressChange.value = address
  }

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

}
