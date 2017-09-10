package com.transportation.letsride.data

import com.transportation.letsride.EspressoDaggerMockRule
import com.transportation.letsride.common.extensions.toLatLng
import com.transportation.letsride.data.repository.AddressRepository
import com.transportation.letsride.data.repository.LocationRepository
import io.reactivex.Maybe
import io.reactivex.Observable
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito

class RepositoryMocks {

  @Rule @JvmField
  val rule = EspressoDaggerMockRule().run { initMocks(this@RepositoryMocks) }

  @Mock
  lateinit var locationRepository: LocationRepository

  @Mock
  lateinit var addressRepository: AddressRepository

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


}
