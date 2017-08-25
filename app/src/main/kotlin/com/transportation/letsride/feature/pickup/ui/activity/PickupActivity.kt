package com.transportation.letsride.feature.pickup.ui.activity

import android.os.Bundle
import com.transportation.letsride.R
import com.transportation.letsride.common.extensions.appComponent
import com.transportation.letsride.common.extensions.attachFragment
import com.transportation.letsride.common.extensions.commitTransactions
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.common.ui.fragment.MapFragment
import com.transportation.letsride.data.model.JourneyEstimate
import com.transportation.letsride.data.model.Stop
import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.feature.pickup.PickupContract
import com.transportation.letsride.feature.pickup.di.DaggerPickupComponent
import com.transportation.letsride.feature.pickup.di.PickupComponent
import com.transportation.letsride.feature.pickup.di.PickupModule
import javax.inject.Inject

class PickupActivity : BaseActivity(), PickupContract.View {

  val component: PickupComponent by lazy {
    DaggerPickupComponent.builder()
        .applicationComponent(application.appComponent())
        .pickupModule(PickupModule(this))
        .build()
  }

  @Inject
  lateinit var categoryRepository: Repository.Category

  @Inject
  lateinit var presenter: PickupContract.Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)
    component.inject(this)

    attachMap()

    doRequest()
  }

  private fun doRequest() {
    JourneyEstimate(
        stops = listOf(
            Stop(
                location = listOf(40.416947, -3.705717),
                name = "Puerta del sol",
                address = "Plaza de la Puerta del Sol",
                number = "s/n",
                city = "Madrid",
                country = "Spain",
                instr = "Hello, world!"
            ),
            Stop(
                location = listOf(40.416947, -3.705717),
                name = "Puerta del sol",
                address = "Plaza de la Puerta del Sol",
                number = "s/n",
                city = "Madrid",
                country = "Spain",
                instr = "Hello, world!"
            )
        )
    ).let { categoryRepository.estimates(it) }
  }

  private fun attachMap() {
    val mapFragment = MapFragment.newInstance()
    supportFragmentManager.commitTransactions {
      it.attachFragment(mapFragment, R.id.pickupMapContainer, MapFragment.TAG)
    }
  }
}
