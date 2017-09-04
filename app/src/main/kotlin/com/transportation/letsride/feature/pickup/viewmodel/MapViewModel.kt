package com.transportation.letsride.feature.pickup.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.toLatLng
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.repository.LocationRepository
import io.reactivex.disposables.Disposables
import timber.log.Timber
import javax.inject.Inject

class MapViewModel @Inject constructor(
    val locationRepository: LocationRepository
) : BaseViewModel() {

  var initialLocationDisposable = Disposables.empty()

  val permissionGranted = MediatorLiveData<Boolean>()
  val myLocationEnabled = MediatorLiveData<Boolean>().apply {
    addSource(permissionGranted) { it?.let { enableMyLocation(it) } }
  }
  //  val pickupAddressChange = MutableLiveData<Address?>()
  val mapDragged = MutableLiveData<LatLng>()
  val currentMapCameraPosition = MediatorLiveData<LatLng>().apply {
    addSource(permissionGranted) { granted -> shouldRetrieveCurrentPosition(granted) }
    addSource(mapDragged) { value = it }

  }
  val balh = MediatorLiveData<LatLng>().apply {
//    addSource(pickupAddressChange) { moveMapToAddress(it) }
  }

  fun enableMyLocation(enabled: Boolean) {
    myLocationEnabled.value = enabled
  }

  fun mapDragged(newPosition: LatLng) {
    mapDragged.value = newPosition
  }

  fun pickupAddressChanged(address: Address?) {
    address?.let { balh.value = address.getLatLng() }

//    pickupAddressChange.postValue(address)
  }

  fun moveMapToMyLocation() {
    retrieveCurrentPosition()
  }

  fun onPermissionGranted(granted: Boolean) {
    permissionGranted.value = granted
  }

  private fun moveMapToAddress(address: Address?) {
    address?.let { currentMapCameraPosition.value = it.getLatLng() }
  }

  private fun shouldRetrieveCurrentPosition(permissionGranted: Boolean?) {
    if (permissionGranted != null) {
      if (permissionGranted) retrieveCurrentPosition()
    }
  }

  private fun retrieveCurrentPosition() {
    initialLocationDisposable = locationRepository.location().take(1)
        .subscribe(
            { newLocation ->
              balh.postValue(newLocation.toLatLng())
              initialLocationDisposable.dispose()
            },
            { error -> Timber.e(error) }
        ).apply { disposables += this }
  }

}
