package com.transportation.letsride.feature.pickup.di

import com.afrodite.common.di.PerActivity
import com.transportation.letsride.data.repository.CategoryRepository
import com.transportation.letsride.data.repository.Repository
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
      view: PickupContract.View,
      categoryRepository: Repository.Category
  ): PickupContract.Presenter {
    return PickupPresenter(view, categoryRepository)
  }


}
