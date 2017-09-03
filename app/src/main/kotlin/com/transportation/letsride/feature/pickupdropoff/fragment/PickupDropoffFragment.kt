package com.transportation.letsride.feature.pickupdropoff.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.common.util.observe
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.feature.pickup.viewmodel.MapViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropoffViewModel
import kotlinx.android.synthetic.main.fragment_pickup_dropoff.*

class PickupDropoffFragment : BaseFragment() {

  val mapViewModel: MapViewModel by unsafeLazy {
    ViewModelProviders.of(activity, viewModelFactory)
        .get(MapViewModel::class.java)
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
    listenData()
    listenViews()
  }

  private fun listenViews() {
    addressPickupView.setOnClickListener { viewModel.onPickupAddressClicked() }
    addressDropoffView.setOnClickListener { viewModel.onDropoffAddressClicked() }
  }

  private fun listenData() {
    mapViewModel.currentMapCameraPosition
        .observe(this) { viewModel.newCurrentLocation(it!!) }

    viewModel.pickupAddressChange.let {
      it.observe(this, this::pickupAddressChanged)
      it.observe(this, mapViewModel::addressChange)
    }
  }

  private fun pickupAddressChanged(address: Address?) {
    addressPickupView.applyAddress(address)
  }

  private fun handleAddressChangeError(error: Throwable) {

  }



  override fun onPause() {
    super.onPause()
  }

  fun showSeachAddressView(currentAddress: String?) {
//    SearchAddressActivity
//        .getIntentWithAddress(activity, currentAddress)
//        .let { startActivity(it) }
  }

  companion object {
    val TAG: String = PickupDropoffFragment::class.java.canonicalName

    fun newInstance(): PickupDropoffFragment {
      return PickupDropoffFragment()
    }
  }

}
