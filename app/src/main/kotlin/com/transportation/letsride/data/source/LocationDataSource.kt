package com.transportation.letsride.data.source

import android.location.Location
import io.reactivex.Observable

interface LocationDataSource {
  fun getLocationsStream(): Observable<Location>
}
