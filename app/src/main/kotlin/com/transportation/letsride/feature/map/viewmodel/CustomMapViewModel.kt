package com.transportation.letsride.feature.map.viewmodel

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.util.Signal
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.repository.Repository
import io.reactivex.internal.operators.flowable.FlowablePublish
import io.reactivex.subjects.CompletableSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.SingleSubject
import timber.log.Timber
import javax.inject.Inject

class CustomMapViewModel @Inject constructor() : BaseViewModel() {

  val mapReady: CompletableSubject = CompletableSubject.create()
  val mapDragged: PublishSubject<LatLng> = PublishSubject.create<LatLng>()

  fun mapReady() {
    mapReady.onComplete()
  }

  fun mapDragged(latLng: LatLng) {
    mapDragged.onNext(latLng)
  }

}
