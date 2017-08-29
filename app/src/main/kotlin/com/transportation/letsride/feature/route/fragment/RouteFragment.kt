package com.transportation.letsride.feature.route.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.feature.search.activity.SearchAddressActivity

class RouteFragment : BaseFragment() {

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
    val TAG: String = RouteFragment::class.java.canonicalName

    fun newInstance(): RouteFragment {
      return RouteFragment()
    }
  }

}
