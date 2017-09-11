package com.transportation.letsride.feature.pickupdropoff.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.repository.AddressRepository
import com.transportation.letsride.feature.pickup.viewmodel.MapCameraPositionAction
import timber.log.Timber
import javax.inject.Inject

typealias FilledAddresses = Pair<PinPoint?, PinPoint?>

class PickupDropOffViewModel @Inject constructor(
    private val addressRepository: AddressRepository,
    private val schedulers: SchedulerProvider
) : BaseViewModel() {

  val pickupAddressChange = MutableLiveData<PinPoint?>()
  val dropOffAddressChange = MutableLiveData<PinPoint?>()

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

  override fun restoreState(savedInstanceState: Bundle) {
    val state = savedInstanceState.get(PickupDropOffViewModelState.KEY_STATE) as PickupDropOffViewModelState
    state.apply {
      pickupAddress?.let { pickupAddressChange.value = it }
      dropOffAddress?.let { dropOffAddressChange.value = it }
    }
  }

  override fun saveState(): Parcelable {
    return PickupDropOffViewModelState(
        pickupAddressChange.value,
        dropOffAddressChange.value
    )
  }

  fun newMapCameraPosition(mapCameraPositionAction: MapCameraPositionAction?) {
    when (mapCameraPositionAction) {
      is MapCameraPositionAction.MapDragged -> findAddress(mapCameraPositionAction.newLocation)
      is MapCameraPositionAction.JustMoveMap -> findAddress(mapCameraPositionAction.newLocation)
    }
  }

  fun onPickupAddressClicked() {
    navigateToPickupAddressSearch.value = Unit
  }

  fun onDropOffAddressClicked() {
    navigateToDropOffAddressSearch.value = Unit
  }

  fun onReceivePickupAddressResult(pinPoint: PinPoint) {
    pickupAddressChange.value = pinPoint
  }

  fun onReceiveDropOffAddressResult(pinPoint: PinPoint) {
    dropOffAddressChange.value = pinPoint
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
