package com.transportation.letsride.feature.pickup.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.R
import com.transportation.letsride.common.di.FragmentInjector
import com.transportation.letsride.common.extensions.attachFragment
import com.transportation.letsride.common.extensions.commitNowTransactions
import com.transportation.letsride.common.extensions.findFragment
import com.transportation.letsride.common.util.observe
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.map.fragment.CustomMapFragment
import com.transportation.letsride.feature.pickup.viewmodel.MapViewModel
import com.transportation.letsride.feature.pickupdropoff.ui.fragment.PickupDropoffFragment
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropOffViewModel
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_pickup.*
import timber.log.Timber
import javax.inject.Inject

class PickupActivity : BasePickupPermissionActivity(), FragmentInjector, CustomMapFragment.MapListener {

  @Inject
  override lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  val viewModel: MapViewModel by unsafeLazy({
    ViewModelProviders.of(this, viewModelFactory)
        .get(MapViewModel::class.java)
  })

  var mapFragment: CustomMapFragment? = null
    get() = findFragment(CustomMapFragment.TAG)

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.d("onCreate")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)

    if (savedInstanceState == null)
      attachViews()

    listenData()
    listenViews()

  }

  override fun mapDragged(newLocation: LatLng) {
    viewModel.mapDragged(newLocation)
  }

  override fun onLocationPermissionGranted() {
    viewModel.onPermissionGranted(true)
  }

  override fun onLocationPermissionDenied() {
    viewModel.onPermissionGranted(false)
  }

  private fun attachViews() {
    supportFragmentManager.commitNowTransactions {
      it.attachFragment(CustomMapFragment.newInstance(), pickupMapContainer.id, CustomMapFragment.TAG)
      it.attachFragment(PickupDropoffFragment.newInstance(), pickupDropoffAddressContainer.id, PickupDropoffFragment.TAG)
    }
  }

  private fun listenViews() {
    buttonPickupMyLocation.setOnClickListener { viewModel.moveMapToMyLocation() }
  }

  private fun listenData() {
    viewModel.myLocationEnabled
        .observe(this, this::showMyLocationButton)
//    viewModel.currentMapCameraPosition
//        .observe(this, this::moveMapToLocation)
        viewModel.balh
        .observe(this, this::moveMapToLocation)
  }

  private fun moveMapToLocation(latLng: LatLng?) {
    mapFragment?.moveMapToLocation(latLng)
  }

  private fun showMyLocationButton(enabled: Boolean?) {
    when (enabled) {
      true -> buttonPickupMyLocation.show()
      false -> buttonPickupMyLocation.hide()
    }
  }

}
