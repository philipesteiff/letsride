package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.model.AutoCompleteOptions
import com.transportation.letsride.data.model.Prediction
import io.reactivex.Single

interface AddressRepository {
  fun getAddressFromLocation(latLng: LatLng): Single<Address?>
  fun query(input: String, options: AutoCompleteOptions): Single<List<Prediction>>
  fun reverseGeocode(placeId: String): Single<Address>
}
