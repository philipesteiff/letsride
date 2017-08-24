package com.transportation.letsride.feature.pickup.di

import com.afrodite.common.di.PerActivity
import com.transportation.letsride.feature.pickup.PickupContract
import com.transportation.letsride.feature.pickup.presenter.PickupPresenter
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import dagger.Module
import dagger.Provides

@Module
class PickupModule(val target: PickupActivity) {

  @Provides
  @PerActivity
  fun providePickUpView(): PickupContract.View = target

  @Provides
  @PerActivity
  fun provideNewPickUpPresenter(
      view: PickupContract.View
  ): PickupContract.Presenter {
    return PickupPresenter(view)
  }


}
