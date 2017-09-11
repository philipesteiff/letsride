package com.transportation.letsride.feature.pickup.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.toLatLng
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.repository.LocationRepository
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses
import io.reactivex.disposables.Disposables
import timber.log.Timber
import javax.inject.Inject

sealed class MapCameraPositionAction {
  class JustMoveMap(val newLocation: LatLng) : MapCameraPositionAction()
  class MapDragged(val newLocation: LatLng) : MapCameraPositionAction()
  class AdjustMap(val newLocation: LatLng) : MapCameraPositionAction()
}

typealias PickupDropOffPinPositions = Pair<LatLng?, LatLng?>

class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val schedulers: SchedulerProvider
) : BaseViewModel() {

  var initialLocationDisposable = Disposables.empty()

  val isPickupMarkerVisible = MutableLiveData<Boolean>()

  val enableMapDrag = MutableLiveData<Boolean>()

  val isPermissionGranted = MediatorLiveData<Boolean>()
  val isMyLocationEnabled = MediatorLiveData<Boolean>().apply {
    addSource(isPermissionGranted) { it?.let { enableMyLocation(it) } }
  }

  val mapCameraPosition = MediatorLiveData<MapCameraPositionAction>().apply {
    addSource(isPermissionGranted) { granted -> shouldRetrieveCurrentPosition(granted) }
  }

  val pickupDropOffPinPositions = MutableLiveData<PickupDropOffPinPositions>()

  fun enableMyLocation(enabled: Boolean) {
    isMyLocationEnabled.value = enabled
  }

  fun mapDragged(newPosition: LatLng) {
    mapCameraPosition.value = MapCameraPositionAction.MapDragged(newPosition)
  }

  fun moveMapToMyLocation() {
    retrieveCurrentPosition()
  }

  fun onPermissionGranted(granted: Boolean) {
    isPermissionGranted.value = granted
  }

  fun moveToPickupAddressLocation(location: LatLng?) {
    initialLocationDisposable.dispose()
    location?.let { mapCameraPosition.value = MapCameraPositionAction.AdjustMap(it) }
  }

  fun pickupDropOffAddressFilled(filledAddresses: FilledAddresses?) {
    filledAddresses?.let { (pickupAddress, dropOffAddress) ->
      pickupDropOffPinPositions.value = PickupDropOffPinPositions(
          pickupAddress?.getLatLng(),
          dropOffAddress?.getLatLng()
      )

    }
  }

  fun stateChanged(state: ViewState?) {
    when (state) {
      ViewState.INIT -> {
        enableMapDrag.value = true
        isPickupMarkerVisible.value = true
      }
      ViewState.ESTIMATE -> {
        enableMapDrag.value = false
        isPickupMarkerVisible.value = false
      }
    }
  }

  private fun shouldRetrieveCurrentPosition(permissionGranted: Boolean?) {
    if (permissionGranted != null) {
      if (permissionGranted) retrieveCurrentPosition()
    }
  }

  private fun retrieveCurrentPosition() {
    initialLocationDisposable = locationRepository.location()
        .take(1)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribe(
            { newLocation ->
              mapCameraPosition.value = MapCameraPositionAction.JustMoveMap(newLocation.toLatLng())
              initialLocationDisposable.dispose()
            },
            { error -> Timber.e(error) }
        ).apply { disposables += this }
  }


}
