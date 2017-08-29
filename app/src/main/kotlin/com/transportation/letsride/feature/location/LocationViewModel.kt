package com.transportation.letsride.feature.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.transportation.letsride.common.util.unsafeLazy


class LocationViewModel(application: Application) : AndroidViewModel(application) {

  val locationLiveData by unsafeLazy {
    //    LocationLiveData(this, { requestLocationPermission() })
  }

}
