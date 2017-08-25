package com.transportation.letsride.feature.pickup.presenter

import com.transportation.letsride.data.repository.Repository
import com.transportation.letsride.feature.pickup.PickupContract

class PickupPresenter(
    view: PickupContract.View,
    categoryRepository: Repository.Category
) : PickupContract.Presenter {

}
