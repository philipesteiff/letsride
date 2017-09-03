package com.transportation.letsride.feature.pickup.ui.activity

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.activity.BaseActivity
import permissions.dispatcher.*

@RuntimePermissions
abstract class BasePickupPermissionActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    BasePickupPermissionActivityPermissionsDispatcher
        .onLocationPermissionGrantedWithCheck(this)
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    BasePickupPermissionActivityPermissionsDispatcher.
        onRequestPermissionsResult(this, requestCode, grantResults)
  }

  @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
  abstract fun onLocationPermissionGranted()

  @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
  fun showRationaleForLocationPermission(request: PermissionRequest) {
    AlertDialog.Builder(this)
        .setMessage(R.string.permission_location_rationale)
        .setPositiveButton(R.string.button_allow, { _, _ -> request.proceed() })
        .setNegativeButton(R.string.button_deny, { _, _ -> request.cancel() })
        .show()
  }

  @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
  fun showPermissionDeniedMessageForLocation() {
    Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_SHORT).show()
  }

  @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
  fun showNeverAskForLocationPermission() {
    Toast.makeText(this, R.string.location_permission_permanently_denied_message, Toast.LENGTH_SHORT).show()
  }

}
