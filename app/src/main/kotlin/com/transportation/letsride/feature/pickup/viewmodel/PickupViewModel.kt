package com.transportation.letsride.feature.pickup.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.model.Address
import javax.inject.Inject

class PickupViewModel @Inject constructor() : BaseViewModel() {

  val addressChange = MutableLiveData<Address?>()
  val mapDragged = MutableLiveData<LatLng>()

  fun mapDragged(latLng: LatLng) {
    mapDragged.value = latLng
  }

  fun addressChange(address: Address?) {
    addressChange.postValue(address)
  }

}
