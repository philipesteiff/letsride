package com.transportation.letsride.common.navigation

import android.content.Context
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.feature.search.activity.SearchAddressActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {

  fun navigateToAddressSearch(context: Context, address: Address?) {
    SearchAddressActivity
        .getIntentWithAddress(context, address)
        .let { context.startActivity(it) }
  }

}
