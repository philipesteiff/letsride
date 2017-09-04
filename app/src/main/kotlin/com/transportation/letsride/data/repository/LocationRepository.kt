package com.transportation.letsride.data.repository

import android.location.Location
import io.reactivex.Observable

interface LocationRepository {
  fun location(): Observable<Location>
}
