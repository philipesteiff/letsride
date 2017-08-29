package com.transportation.letsride.feature.route.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.feature.route.RouteContract
import com.transportation.letsride.feature.search.activity.SearchAddressActivity
import kotlinx.android.synthetic.main.fragment_route.*
import javax.inject.Inject

class RouteFragment : BaseFragment(), RouteContract.View {

  @Inject
  lateinit var routePresenter: RouteContract.Presenter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_route, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    routePresenter.bindView(this)
    addressPickupView.setOnAddressClickListener { address -> routePresenter.pickupAddressClick() }
    addressDropoffView.setOnAddressClickListener { address -> routePresenter.dropoffAddressClick() }
  }

  override fun showSeachAddressView(currentAddress: String?) {
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
