package com.transportation.letsride.common.navigation

import android.support.v4.app.Fragment
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.feature.search.activity.SearchAddressActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

  fun navigateToAddressSearchWithAddress(fragment: Fragment, requestCode: Int, address: Address?) {
    SearchAddressActivity
        .getIntentWithAddress(fragment.activity, address)
        .let { fragment.startActivityForResult(it, requestCode) }
  }

}
