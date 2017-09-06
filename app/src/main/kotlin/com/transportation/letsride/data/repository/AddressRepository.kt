package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.PinPoint
import io.reactivex.Maybe

interface AddressRepository {
  fun query(input: String): Maybe<List<PinPoint>>
  fun getAddressFromLocation(latLng: LatLng): Maybe<PinPoint>
}
