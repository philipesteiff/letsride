package com.transportation.letsride.data.repository

import android.location.Location
import com.transportation.letsride.data.source.LocationDataSource
import io.reactivex.Observable

class LocationRepositoryImpl(
    private val locationDataSource: LocationDataSource
) : LocationRepository {

  override fun location(): Observable<Location> {
    return locationDataSource.getLocationsStream()
  }

}
