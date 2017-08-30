package com.transportation.letsride.data.repository

import com.transportation.letsride.data.source.LocationDataSource
import com.transportation.letsride.data.source.LocationSourceResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class LocationRepository(
    val locationDataSource: LocationDataSource
) : Repository.Location {

  override fun location(): Flowable<LocationSourceResponse> {
    return locationDataSource.locations.toFlowable(BackpressureStrategy.LATEST)
  }

}
