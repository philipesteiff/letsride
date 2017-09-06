package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.source.AddressDataSource
import io.reactivex.Maybe

class AddressRepositoryImpl(
    private val addressDataSource: AddressDataSource
) : AddressRepository {

  override fun query(input: String): Maybe<List<PinPoint>> {
    return addressDataSource.findAddresses(input)
  }

  override fun getAddressFromLocation(latLng: LatLng): Maybe<PinPoint> {
    return addressDataSource.findAddressByLocation(latLng)
  }

}
