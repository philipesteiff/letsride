package com.transportation.letsride.feature.route

import com.transportation.letsride.common.Presentable

object RouteContract {

  interface Presenter: Presentable<RouteContract.View> {
    fun pickupAddressClick()
    fun dropoffAddressClick()
  }

  interface View {
    fun showSeachAddressView(currentAddress: String?)
  }

}
