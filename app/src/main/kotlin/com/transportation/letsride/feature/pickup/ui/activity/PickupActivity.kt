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
import com.transportation.letsride.feature.pickupdropoff.fragment.PickupDropoffFragment
import dagger.android.DispatchingAndroidInjector
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_pickup.*
import javax.inject.Inject

class PickupActivity : BasePickupPermissionActivity(), FragmentInjector, CustomMapFragment.MapListener {

  @Inject
  override lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  val viewModel: MapViewModel by unsafeLazy({
    ViewModelProviders.of(this, viewModelFactory)
        .get(MapViewModel::class.java)
  })

  // region Fragments

  var mapFragment: CustomMapFragment? = null
    get() = findFragment(CustomMapFragment.TAG)

  var pickupDropoffFragment: PickupDropoffFragment? = null
    get() = findFragment(PickupDropoffFragment.TAG)

  // endregion

  private val disposables = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)

    if (savedInstanceState == null)
      attachViews()

    listenData()
    listenViews()
  }

  override fun onDestroy() {
    super.onDestroy()
    disposables.clear()
  }

  override fun mapDragged(newLocation: LatLng) {
    viewModel.mapDragged(newLocation)
  }

  override fun onLocationPermissionGranted() {
    viewModel.enableMyLocation()
  }

  private fun attachViews() {
    supportFragmentManager.commitNowTransactions {
      it.attachFragment(CustomMapFragment.newInstance(), pickupMapContainer.id, CustomMapFragment.TAG)
      it.attachFragment(PickupDropoffFragment.newInstance(), pickupRouteContainer.id, PickupDropoffFragment.TAG)
    }
  }

  private fun listenViews() {
    buttonPickupMyLocation.setOnClickListener { viewModel.moveMapToMyLocation() }
  }

  private fun listenData() {
    viewModel.myLocationEnabled
        .observe(this, this::showMyLocationButton)
    viewModel.currentMapCameraPosition
        .observe(this, this::moveMapToLocation)

  }

  private fun moveMapToLocation(latLng: LatLng) {
    mapFragment?.moveMapToLocation(latLng)
  }

  private fun showMyLocationButton(enabled: Boolean) {
    when (enabled) {
      true -> buttonPickupMyLocation.show()
      false -> buttonPickupMyLocation.hide()
    }
  }

}
