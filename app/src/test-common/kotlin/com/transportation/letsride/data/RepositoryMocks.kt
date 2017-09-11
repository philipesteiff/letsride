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
    val hqLocation = Fabricator.hqLocation()
    val hqPinPoint = Fabricator.hqPinPoint()
    Mockito.`when`(addressRepository.getAddressFromLocation(hqLocation.toLatLng()))
        .thenReturn(Maybe.just(hqPinPoint))
    Mockito.`when`(locationRepository.location())
        .thenReturn(Observable.just(hqLocation))
  }

  fun mockPuertaDelSolLocation() {
    val puertaDelSolLocation = Fabricator.puertaDelSolLocation()
    val puertaDelSolPinPoint = Fabricator.puertaDelSolPinPoint()
    Mockito.`when`(addressRepository.getAddressFromLocation(puertaDelSolLocation.toLatLng()))
        .thenReturn(Maybe.just(puertaDelSolPinPoint))
    Mockito.`when`(locationRepository.location())
        .thenReturn(Observable.just(puertaDelSolLocation))
  }

  fun mockEstimatesHqToPuertaDelSol() {
    val pickupPinPoint = Fabricator.hqPinPoint()
    val dropOffPinPoint = Fabricator.puertaDelSolPinPoint()
    Mockito.`when`(journeyRepository.estimates(
        pickupPinPoint,
        dropOffPinPoint
    )).thenReturn(Single.just(Fabricator.estimatesHqToPuertaDelSol()))
  }


}
