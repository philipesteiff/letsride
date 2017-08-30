package com.transportation.letsride.feature.location

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.repository.Repository
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PickupViewModel @Inject constructor(
    location: Repository.Location
) : BaseViewModel() {

  sealed class Model {

  }

  fun listenOnDragFinished(onDragFinished: PublishSubject<LatLng>?) {


  }

  fun onMapReady() {

  }

  fun onMapDragged(latLng: LatLng) {

  }


}
