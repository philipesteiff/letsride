package com.transportation.letsride.data.repository

import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.data.source.AddressDataSource
import com.transportation.letsride.data.source.LocationDataSource
import com.transportation.letsride.data.source.LocationSourceResponse
import io.reactivex.Observable
import io.reactivex.Single

class LocationRepository(
    private val locationDataSource: LocationDataSource,
    private val addressDataSource: AddressDataSource
) : Repository.Location {

  override fun location(): Observable<LocationSourceResponse> {
    return locationDataSource.getLocationsStream()
  }

  override fun getAddressFromLocation(latLng: LatLng): Single<Address?> {
    return addressDataSource.findAddressByLocation(latLng)
  }

}
