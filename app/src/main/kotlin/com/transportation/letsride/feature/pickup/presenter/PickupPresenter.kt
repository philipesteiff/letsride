package com.transportation.letsride.feature.pickup.presenter

import android.os.Bundle
import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.feature.pickup.PickupContract

class PickupPresenter(
    categoryRepository: Repository.Category
) : PickupContract.Presenter {

  lateinit private var view: PickupContract.View

  override fun bindView(view: PickupContract.View) {
    this.view = view
  }

  override fun onViewReady(savedInstanceState: Bundle?, extras: Bundle?) {

  }

  override fun onSaveInstanceState(bundle: Bundle?) {

  }
}
