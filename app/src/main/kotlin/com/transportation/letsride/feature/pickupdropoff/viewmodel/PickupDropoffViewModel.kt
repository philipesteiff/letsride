package com.transportation.letsride.feature.pickupdropoff.viewmodel

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.viewmodel.BaseViewModel
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.repository.Repository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class PickupDropoffViewModel @Inject constructor(
    private val locationRepository: Repository.Location,
    private val schedulers: SchedulerProvider
) : BaseViewModel() {

  private val currentLocation: BehaviorSubject<LatLng> = BehaviorSubject.create<LatLng>()

  fun newCurrentLocation(latLng: LatLng) {
    currentLocation.onNext(latLng)
  }

  fun getAddressChangeStream(): Observable<Address?> = currentLocation
      .doOnNext { Timber.d("AeeeHOo 1 / $it")}
      .flatMapSingle(locationRepository::getAddressFromLocation)
      .doOnNext { Timber.d("AeeeHOo 2 / $it")}
      .subscribeOn(schedulers.io())
      .observeOn(schedulers.ui())



}
