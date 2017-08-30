package com.transportation.letsride.feature.location

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.google.android.gms.common.api.GoogleApiClient
import com.transportation.letsride.common.util.unsafeLazy
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    application: Application,
    googleApiClient: GoogleApiClient
) : AndroidViewModel(application) {

  val locationLiveData by unsafeLazy { LocationLiveData(googleApiClient) }

}
