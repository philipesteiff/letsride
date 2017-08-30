package com.transportation.letsride.feature.pickupdropoff.viewmodel

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.viewmodel.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PickupDropoffViewModel @Inject constructor() : BaseViewModel() {

  val mapDragged = Observable.empty<LatLng>()
//  COMO SE PLUGAR NO MAPDRAGGED QUE PASSA PELA ACTIVITY ? ? ? ?. ?

}
