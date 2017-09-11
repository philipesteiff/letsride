package com.transportation.letsride.feature.pickup.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.R
import com.transportation.letsride.common.di.FragmentInjector
import com.transportation.letsride.common.extensions.attachFragment
import com.transportation.letsride.common.extensions.commitTransactions
import com.transportation.letsride.common.extensions.findFragment
import com.transportation.letsride.common.extensions.observe
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.estimate.ui.fragment.EstimatesFragment
import com.transportation.letsride.feature.map.ui.fragment.CustomMapFragment
import com.transportation.letsride.feature.pickup.viewmodel.MapCameraPositionAction
import com.transportation.letsride.feature.pickup.viewmodel.MapViewModel
import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import com.transportation.letsride.feature.pickupdropoff.ui.fragment.PickupDropOffFragment
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_pickup.*
import javax.inject.Inject

class PickupActivity : BasePickupPermissionActivity(), FragmentInjector, CustomMapFragment.MapListener {

  @Inject
  override lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  val mapViewModel: MapViewModel by unsafeLazy({
    ViewModelProviders.of(this, viewModelFactory)
        .get(MapViewModel::class.java)
  })

  val pickupViewModel: PickupViewModel by unsafeLazy({
    ViewModelProviders.of(this, viewModelFactory)
        .get(PickupViewModel::class.java)
  })

  var mapFragment: CustomMapFragment? = null
    get() = findFragment(CustomMapFragment.TAG)

  var estimatesFragment: EstimatesFragment? = null
    get() = findFragment(EstimatesFragment.TAG)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)
    viewPickupPin.centerOn(pickupMapContainer)

    if (savedInstanceState == null)
      attachViews()

    listenData()
    listenViews()
  }

  override fun onBackPressed() {
    if (pickupViewModel.onBackPressed())
      super.onBackPressed()
  }

  override fun mapDragged(newLocation: LatLng) {
    mapViewModel.mapDragged(newLocation)
  }

  override fun onLocationPermissionGranted() {
    mapViewModel.onPermissionGranted(true)
  }

  override fun onLocationPermissionDenied() {
    mapViewModel.onPermissionGranted(false)
  }

  private fun attachViews() {
    supportFragmentManager.commitTransactions {
      it.attachFragment(CustomMapFragment.newInstance(), pickupMapContainer.id, CustomMapFragment.TAG)
      it.attachFragment(PickupDropOffFragment.newInstance(), pickupDropOffAddressContainer.id, PickupDropOffFragment.TAG)
      it.attachFragment(EstimatesFragment.newInstance(), estimatesContainer.id, EstimatesFragment.TAG)
    }
  }

  private fun listenViews() {
    buttonPickupMyLocation.setOnClickListener { mapViewModel.moveMapToMyLocation() }
  }

  private fun listenData() {

    pickupViewModel.viewState
        .observe(this, mapViewModel::stateChanged)

    mapViewModel.isMyLocationEnabled
        .observe(this, this::showMyLocationButton)

    mapViewModel.mapCameraPosition
        .observe(this, this::moveMapToLocation)

    mapViewModel.isPickupMarkerVisible
        .observe(this, this::showPickupMarker)

    mapViewModel.enableMapDrag
        .observe(this, this::enableMapDrag)

    pickupViewModel.isEstimatesVisible
        .observe(this, this::showEstimates)

    pickupViewModel.estimates
        .observe(this, this::loadEstimates)

  }

  private fun moveMapToLocation(action: MapCameraPositionAction?) {
    when (action) {
      is MapCameraPositionAction.JustMoveMap -> mapFragment?.moveMapToLocation(action.newLocation)
      is MapCameraPositionAction.AdjustMap -> mapFragment?.moveMapToLocation(action.newLocation)
    }
  }

  private fun showMyLocationButton(enabled: Boolean?) {
    when (enabled) {
      true -> buttonPickupMyLocation.show()
      false -> buttonPickupMyLocation.hide()
    }
  }

  private fun showPickupMarker(enabled: Boolean?) {
    when (enabled) {
      true -> viewPickupPin.visibility = VISIBLE
      false -> viewPickupPin.visibility = GONE
    }
  }

  private fun enableMapDrag(enabled: Boolean?) {
    enabled?.let { mapFragment?.setMapDragEnabled(it) }
  }

  private fun showEstimates(enabled: Boolean?) {
    when (enabled) {
      true -> estimatesContainer.visibility = VISIBLE
      false -> estimatesContainer.visibility = GONE
    }
  }

  private fun loadEstimates(filledAddresses: FilledAddresses?) {
    estimatesFragment?.loadEstimatesWith(filledAddresses)
  }

}
