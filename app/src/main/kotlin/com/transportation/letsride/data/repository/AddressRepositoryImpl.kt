package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.model.AutoCompleteOptions
import com.transportation.letsride.data.model.Prediction
import com.transportation.letsride.data.source.AddressDataSource
import io.reactivex.Single

class AddressRepositoryImpl(
    private val addressDataSource: AddressDataSource
) : AddressRepository {

  override fun query(input: String, options: AutoCompleteOptions): Single<List<Prediction>> {
    return addressDataSource.query(input, options)
  }

  override fun getAddressFromLocation(latLng: LatLng): Single<PinPoint?> {
    return addressDataSource.findAddressByLocation(latLng)
  }

  override fun reverseGeocode(placeId: String): Single<PinPoint> {
    return addressDataSource.reverseGeocode(placeId)
  }
}
