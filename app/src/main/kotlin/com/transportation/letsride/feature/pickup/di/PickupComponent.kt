package com.transportation.letsride.feature.pickup.di

import com.afrodite.common.di.PerActivity
import com.transportation.letsride.common.di.component.ApplicationComponent
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import dagger.Component


@PerActivity
@Component(
    dependencies = arrayOf(ApplicationComponent::class),
    modules = arrayOf(
        PickupModule::class
    )
)
interface PickupComponent {
  fun inject(pickUpActivity: PickupActivity)
}
