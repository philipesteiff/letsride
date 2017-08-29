package com.transportation.letsride.feature.map.presenter

import android.os.Bundle
import com.transportation.letsride.feature.map.MapControlsContract

class MapControlsPresenter : MapControlsContract.Presenter {

  lateinit private var view: MapControlsContract.View

  override fun bindView(view: MapControlsContract.View) {
    this.view = view
  }

  override fun onViewReady(savedInstanceState: Bundle?, extras: Bundle?) {

  }

  override fun onSaveInstanceState(bundle: Bundle?) {

  }

}
