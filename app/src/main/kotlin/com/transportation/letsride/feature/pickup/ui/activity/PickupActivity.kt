package com.transportation.letsride.feature.pickup.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.transportation.letsride.R
import com.transportation.letsride.common.di.FragmentInjector
import com.transportation.letsride.common.extensions.attachFragment
import com.transportation.letsride.common.extensions.commitNowTransactions
import com.transportation.letsride.common.extensions.findFragment
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.feature.map.fragment.CustomMapFragment
import com.transportation.letsride.feature.map.fragment.MapControlsFragment
import com.transportation.letsride.feature.pickup.PickupContract
import com.transportation.letsride.feature.route.fragment.RouteFragment
import dagger.android.DispatchingAndroidInjector
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_pickup.*
import timber.log.Timber
import javax.inject.Inject

class PickupActivity : BaseActivity(), PickupContract.View, FragmentInjector {

  @Inject
  override lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  private var subscription: Disposable = Disposables.empty()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)

    val mapFragment = CustomMapFragment.newInstance()
    val routeFragment = RouteFragment.newInstance()
    val mapControlsFragment = MapControlsFragment.newInstance()
    supportFragmentManager.commitNowTransactions {
      it.attachFragment(mapFragment, pickupMapContainer.id, CustomMapFragment.TAG)
      it.attachFragment(mapControlsFragment, pickupMapControlsContainer.id, MapControlsFragment.TAG)
      it.attachFragment(routeFragment, pickupRouteContainer.id, RouteFragment.TAG)
    }

    findFragment<CustomMapFragment>(CustomMapFragment.TAG)?.let {
      //      subscribeMapEvents(it)
    }

  }

  private fun subscribeMapEvents(customMapFragment: CustomMapFragment) {
    subscription = customMapFragment.mapReady.subscribe(
        { Timber.d("Foi: $it") }
    )
  }

  override fun onDestroy() {
    super.onDestroy()
    subscription.dispose()
  }


//  private fun doRequest() {
//    JourneyEstimate(
//        stops = listOf(
//            Stop(
//                location = listOf(40.416947, -3.705717),
//                name = "Puerta del sol",
//                address = "Plaza de la Puerta del Sol",
//                number = "s/n",
//                city = "Madrid",
//                country = "Spain",
//                instr = "Hello, world!"
//            ),
//            Stop(
//                location = listOf(40.416947, -3.705717),
//                name = "Puerta del sol",
//                address = "Plaza de la Puerta del Sol",
//                number = "s/n",
//                city = "Madrid",
//                country = "Spain",
//                instr = "Hello, world!"
//            )
//        )
//    ).let { categoryRepository.estimates(it) }
//  }

}
