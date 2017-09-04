package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.formatWithComma
import com.transportation.letsride.data.api.GoogleMapsApi
import com.transportation.letsride.data.executor.SchedulerProvider
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.model.AutoCompleteOptions
import com.transportation.letsride.data.model.GoogleMapsResponseStatus
import com.transportation.letsride.data.model.Prediction
import com.transportation.letsride.data.source.AddressDataSource
import io.reactivex.Single

class AddressRepositoryImpl(
    private val addressDataSource: AddressDataSource
) : AddressRepository {

  override fun query(input: String, options: AutoCompleteOptions): Single<List<Prediction>> {
    return addressDataSource.query(input, options)
  }

  override fun getAddressFromLocation(latLng: LatLng): Single<Address?> {
    return addressDataSource.findAddressByLocation(latLng)
  }

  override fun reverseGeocode(placeId: String): Single<Address> {
    return addressDataSource.reverseGeocode(placeId)
  }
}
