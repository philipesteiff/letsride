package com.transportation.letsride.data

import com.transportation.letsride.common.extensions.toLatLng
import com.transportation.letsride.data.repository.AddressRepository
import com.transportation.letsride.data.repository.JourneyRepository
import com.transportation.letsride.data.repository.LocationRepository
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import org.mockito.Mock
import org.mockito.Mockito

open class RepositoryMocks {

  @Mock
  lateinit var locationRepository: LocationRepository

  @Mock
  lateinit var addressRepository: AddressRepository

  @Mock
  lateinit var journeyRepository: JourneyRepository

  fun mockHqAddressLocation() {
    Mockito.`when`(addressRepository.getAddressFromLocation(Fabricator.hqLocation().toLatLng()))
        .thenReturn(Maybe.just(Fabricator.hqPinPoint()))
    Mockito.`when`(locationRepository.location())
        .thenReturn(Observable.just(Fabricator.hqLocation()))
  }

  fun mockPuertaDelSolLocation() {
    Mockito.`when`(addressRepository.getAddressFromLocation(Fabricator.puertaDelSolLocation().toLatLng()))
        .thenReturn(Maybe.just(Fabricator.puertaDelSolPinPoint()))
    Mockito.`when`(locationRepository.location())
        .thenReturn(Observable.just(Fabricator.puertaDelSolLocation()))
  }

  fun mockEstimatesHqToPuertaDelSol() {
    Mockito.`when`(journeyRepository.estimates(
        Fabricator.hqPinPoint(),
        Fabricator.puertaDelSolPinPoint()
    )).thenReturn(Single.just(Fabricator.estimatesHqToPuertaDelSol()))
  }


}
