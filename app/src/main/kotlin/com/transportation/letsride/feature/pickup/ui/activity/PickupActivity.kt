package com.transportation.letsride.feature.pickup.ui.activity

import android.os.Bundle
import com.transportation.letsride.R
import com.transportation.letsride.common.extensions.appComponent
import com.transportation.letsride.common.ui.activity.BaseActivity
import com.transportation.letsride.common.ui.fragment.MapFragment
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
  lateinit var presenter: PickupContract.Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pickup)
    component.inject(this)

    MapFragment
        .newInstance()
        .mapReady
  }
}
