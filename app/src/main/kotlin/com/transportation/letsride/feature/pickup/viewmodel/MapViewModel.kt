package com.transportation.letsride.feature.pickup.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.toLatLng
import com.transportation.letsride.common.util.plusAssign
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.repository.Repository
import io.reactivex.disposables.Disposables
import timber.log.Timber
import javax.inject.Inject

class MapViewModel @Inject constructor(
    val locationRepository: Repository.Location
) : BaseViewModel() {

  val myLocationEnabled = MutableLiveData<Boolean>()
  val addressChange = MutableLiveData<Address?>()
  val mapDragged = MutableLiveData<LatLng>()
  val currentMapCameraPosition = MediatorLiveData<LatLng>().apply {
    addSource(mapDragged) { value = it }
  }

  var initialLocationDisposable = Disposables.empty()

  init {
    retrieveCurrentPosition()
  }

  fun enableMyLocation() {
    myLocationEnabled.value = true
  }

  fun mapDragged(newPosition: LatLng) {
    mapDragged.value = newPosition
  }

  fun addressChange(address: Address?) {
    addressChange.postValue(address)
  }

  fun moveMapToMyLocation() {
    retrieveCurrentPosition()
  }

  private fun retrieveCurrentPosition() {
    initialLocationDisposable = locationRepository.location().take(1)
        .subscribe(
            { newLocation ->
              currentMapCameraPosition.postValue(newLocation.toLatLng())
              initialLocationDisposable.dispose()
            },
            { error -> Timber.e(error) }
        ).apply { disposables += this }
  }

}
