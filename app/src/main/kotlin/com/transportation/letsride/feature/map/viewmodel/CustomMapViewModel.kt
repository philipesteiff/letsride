package com.transportation.letsride.feature.map.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.model.Address
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CustomMapViewModel @Inject constructor() : BaseViewModel() {

  val mapReady = MutableLiveData<Unit>()
  val mapDragged = MutableLiveData<LatLng>()
  val currentMapPosition = MutableLiveData<LatLng>()

  fun mapReady() {
    mapReady.value = Unit
  }

  fun mapDragged(latLng: LatLng) {
    mapDragged.value = latLng
  }

  fun addressChange(address: Address?) {
    address?.let {
      currentMapPosition.value = LatLng(it.latitude, it.longitude)
    }

  }

}
