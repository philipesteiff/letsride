package com.transportation.letsride.feature.pickup.ui.activity

import android.Manifest
import android.arch.lifecycle.Observer
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.transportation.letsride.R
import com.transportation.letsride.common.di.FragmentInjector
import com.transportation.letsride.common.extensions.attachFragment
import com.transportation.letsride.common.extensions.commitNowTransactions
import com.transportation.letsride.common.extensions.findFragment
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.common.util.asPublisher
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.location.LocationLiveData
import com.transportation.letsride.feature.map.fragment.CustomMapFragment
import com.transportation.letsride.feature.map.fragment.MapControlsFragment
import com.transportation.letsride.feature.route.fragment.RouteFragment
import dagger.android.DispatchingAndroidInjector
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_pickup.pickupMapContainer
import kotlinx.android.synthetic.main.activity_pickup.pickupMapControlsContainer
import kotlinx.android.synthetic.main.activity_pickup.pickupRouteContainer
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import timber.log.Timber
import javax.inject.Inject

class PickupActivity : BaseActivity(), FragmentInjector {

  private val REQUEST_CODE_LOCATION_PERMISSION = 1

  @Inject
  override lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)
    attachViews()
  }

  private fun attachViews() {
    supportFragmentManager.commitNowTransactions {
      it.attachFragment(CustomMapFragment.newInstance(), pickupMapContainer.id, CustomMapFragment.TAG)
      it.attachFragment(MapControlsFragment.newInstance(), pickupMapControlsContainer.id, MapControlsFragment.TAG)
      it.attachFragment(RouteFragment.newInstance(), pickupRouteContainer.id, RouteFragment.TAG)
    }
  }

  private fun requestLocationPermission() {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        REQUEST_CODE_LOCATION_PERMISSION
    )
  }

}
