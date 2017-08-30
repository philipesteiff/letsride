package com.transportation.letsride.feature.pickup.ui.activity

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.transportation.letsride.R
import com.transportation.letsride.common.di.FragmentInjector
import com.transportation.letsride.common.extensions.attachFragment
import com.transportation.letsride.common.extensions.commitNowTransactions
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.location.LocationViewModel
import com.transportation.letsride.feature.map.fragment.CustomMapFragment
import com.transportation.letsride.feature.map.fragment.MapControlsFragment
import com.transportation.letsride.feature.route.fragment.RouteFragment
import dagger.android.DispatchingAndroidInjector
import kotlinx.android.synthetic.main.activity_pickup.*
import permissions.dispatcher.*
import javax.inject.Inject


@RuntimePermissions
class PickupActivity : BaseActivity(), FragmentInjector {

  @Inject
  override lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  val viewModel: LocationViewModel by unsafeLazy {
    ViewModelProviders.of(this, viewModelFactory)
        .get(LocationViewModel::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)

    if (savedInstanceState == null) {
      attachViews()
    }

    PickupActivityPermissionsDispatcher.listenLocationsWithCheck(this)
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    PickupActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
  }

  private fun attachViews() {
    supportFragmentManager.commitNowTransactions {
      it.attachFragment(CustomMapFragment.newInstance(), pickupMapContainer.id, CustomMapFragment.TAG)
      it.attachFragment(MapControlsFragment.newInstance(), pickupMapControlsContainer.id, MapControlsFragment.TAG)
      it.attachFragment(RouteFragment.newInstance(), pickupRouteContainer.id, RouteFragment.TAG)
    }
  }

  @NeedsPermission(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
  fun listenLocations() {

  }

  @OnShowRationale(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
  fun showRationaleForLocationPermission(request: PermissionRequest) {
    AlertDialog.Builder(this)
        .setMessage(R.string.permission_location_rationale)
        .setPositiveButton(R.string.button_allow, { _, _ -> request.proceed() })
        .setNegativeButton(R.string.button_deny, { _, _ -> request.cancel() })
        .show()
  }


  @OnPermissionDenied(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
  fun showPermissionDeniedMessageForLocation() {
    Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show()
  }

  @OnNeverAskAgain(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
  fun showNeverAskForLocationPermission() {
    Toast.makeText(this, R.string.location_permission_permanently_denied_message, Toast.LENGTH_SHORT).show()
  }

}
