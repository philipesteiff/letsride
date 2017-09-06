package com.transportation.letsride.common.navigation

import android.support.v4.app.Fragment
import com.transportation.letsride.feature.search.ui.activity.SearchAddressActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

  fun navigateToAddressSearch(fragment: Fragment, requestCode: Int) {
    SearchAddressActivity
        .getIntent(fragment.activity)
        .let { fragment.startActivityForResult(it, requestCode) }
  }

}
