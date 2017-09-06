package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.data.model.AutoCompleteOptions
import com.transportation.letsride.data.model.Prediction
import io.reactivex.Single

interface AddressRepository {
  fun getAddressFromLocation(latLng: LatLng): Single<PinPoint?>
  fun query(input: String, options: AutoCompleteOptions): Single<List<Prediction>>
  fun reverseGeocode(placeId: String): Single<PinPoint>
}
