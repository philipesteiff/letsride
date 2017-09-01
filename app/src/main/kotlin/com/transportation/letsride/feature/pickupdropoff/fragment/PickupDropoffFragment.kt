package com.transportation.letsride.feature.pickupdropoff.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropoffViewModel
import com.transportation.letsride.feature.search.activity.SearchAddressActivity
import kotlinx.android.synthetic.main.fragment_pickup_dropoff.addressPickupView

class PickupDropoffFragment : BaseFragment() {

  val sharedViewModel: PickupViewModel by unsafeLazy {
    ViewModelProviders.of(activity, viewModelFactory)
        .get(PickupViewModel::class.java)
  }

  val viewModel: PickupDropoffViewModel by unsafeLazy {
    ViewModelProviders.of(this, viewModelFactory)
        .get(PickupDropoffViewModel::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_pickup_dropoff, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    sharedViewModel.mapDragged
        .observe(this, Observer { viewModel.newCurrentLocation(it!!) })

    viewModel.addressChange.let {
      it.observe(this, Observer(this::addressChange))
      it.observe(this, Observer {
        sharedViewModel.addressChange(it)
      })
    }


//    addressPickupView.setOnAddressClickListener { address -> routePresenter.pickupAddressClick() }
//    addressDropoffView.setOnAddressClickListener { address -> routePresenter.dropoffAddressClick() }
  }


  private fun addressChange(address: Address?) {
    address?.run {
      name.let(addressPickupView::setAddressText)
    }

  }

  private fun handleAddressChangeError(error: Throwable) {

  }


  override fun onPause() {
    super.onPause()
  }

  fun showSeachAddressView(currentAddress: String?) {
    SearchAddressActivity
        .getIntentWithAddress(activity, currentAddress)
        .let { startActivity(it) }
  }

  companion object {
    val TAG: String = PickupDropoffFragment::class.java.canonicalName

    fun newInstance(): PickupDropoffFragment {
      return PickupDropoffFragment()
    }
  }

}
