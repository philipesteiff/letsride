package com.transportation.letsride.feature.pickup.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.R
import com.transportation.letsride.common.di.FragmentInjector
import com.transportation.letsride.common.extensions.attachFragment
import com.transportation.letsride.common.extensions.commitNowTransactions
import com.transportation.letsride.common.extensions.detachFragment
import com.transportation.letsride.common.extensions.findFragment
import com.transportation.letsride.common.util.observe
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.categories.ui.fragment.CategoriesFragment
import com.transportation.letsride.feature.map.fragment.CustomMapFragment
import com.transportation.letsride.feature.pickup.viewmodel.MapCameraPositionAction
import com.transportation.letsride.feature.pickup.viewmodel.MapViewModel
import com.transportation.letsride.feature.pickupdropoff.ui.fragment.PickupDropoffFragment
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_pickup.*
import timber.log.Timber
import javax.inject.Inject

class PickupActivity : BasePickupPermissionActivity(), FragmentInjector, CustomMapFragment.MapListener {

  @Inject
  override lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  val mapViewModel: MapViewModel by unsafeLazy({
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
    mapViewModel.mapDragged(newLocation)
  }

  override fun onLocationPermissionGranted() {
    mapViewModel.onPermissionGranted(true)
  }

  override fun onLocationPermissionDenied() {
    mapViewModel.onPermissionGranted(false)
  }

  private fun attachViews() {
    supportFragmentManager.commitNowTransactions {
      it.attachFragment(CustomMapFragment.newInstance(), pickupMapContainer.id, CustomMapFragment.TAG)
      it.attachFragment(PickupDropoffFragment.newInstance(), pickupDropoffAddressContainer.id, PickupDropoffFragment.TAG)
    }
  }

  private fun listenViews() {
    buttonPickupMyLocation.setOnClickListener { mapViewModel.moveMapToMyLocation() }
  }

  private fun listenData() {
    mapViewModel.myLocationEnabled
        .observe(this, this::showMyLocationButton)

    mapViewModel.mapCameraPosition
        .observe(this, this::moveMapToLocation)

    mapViewModel.showCategories
        .observe(this, this::showCategoriesView)
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

  private fun showCategoriesView(attach: Boolean?) {
    supportFragmentManager.commitNowTransactions {
      when (attach) {
        true -> it.attachFragment(CategoriesFragment.newInstance(), categoriesContainer.id, CategoriesFragment.TAG)
        false -> it.detachFragment(supportFragmentManager, CategoriesFragment.TAG)
      }
    }
  }

}
