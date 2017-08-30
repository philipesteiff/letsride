package com.transportation.letsride.feature.pickupdropoff.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.map.viewmodel.CustomMapViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropoffViewModel
import com.transportation.letsride.feature.search.activity.SearchAddressActivity

class PickupDropoffFragment : BaseFragment() {

  val viewModel: PickupDropoffViewModel by unsafeLazy {
    ViewModelProviders.of(activity, viewModelFactory)
        .get(PickupDropoffViewModel::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_route, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    routePresenter.bindView(this)
//    addressPickupView.setOnAddressClickListener { address -> routePresenter.pickupAddressClick() }
//    addressDropoffView.setOnAddressClickListener { address -> routePresenter.dropoffAddressClick() }
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
