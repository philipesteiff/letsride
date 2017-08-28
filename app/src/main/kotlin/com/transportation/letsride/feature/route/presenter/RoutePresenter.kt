package com.transportation.letsride.feature.route.presenter

import android.os.Bundle
import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.feature.route.RouteContract

class RoutePresenter(
    routeRepository: Repository.Route
) : RouteContract.Presenter {

  lateinit private var view: RouteContract.View

  override fun bindView(view: RouteContract.View) {
    this.view = view
  }

  override fun onViewReady(savedInstanceState: Bundle?, extras: Bundle?) {

  }

  override fun onSaveInstanceState(bundle: Bundle?) {

  }

  override fun pickupAddressClick() {
    view.showSeachAddressView("")
  }

  override fun dropoffAddressClick() {
    view.showSeachAddressView("")
  }

}
