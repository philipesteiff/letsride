package com.transportation.letsride.feature.pickupdropoff.fragment

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.navigation.Navigator
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.common.util.observe
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.data.model.Address
import com.transportation.letsride.feature.pickup.viewmodel.MapViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropOffViewModel
import com.transportation.letsride.feature.search.activity.SearchAddressActivity
import kotlinx.android.synthetic.main.fragment_pickup_dropoff.*
import javax.inject.Inject

class PickupDropoffFragment : BaseFragment() {

  @Inject
  lateinit var navigator: Navigator

  val mapViewModel: MapViewModel by unsafeLazy {
    ViewModelProviders.of(activity, viewModelFactory)
        .get(MapViewModel::class.java)
  }

  val viewModel: PickupDropOffViewModel by unsafeLazy {
    ViewModelProviders.of(this, viewModelFactory)
        .get(PickupDropOffViewModel::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_pickup_dropoff, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    listenData()
    listenViews()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      PICKUP_ADDRESS_REQUEST -> shouldExtractData(resultCode, data) { viewModel.onReceivePickupAddressResult(it) }
      DROPOFF_ADDRESS_REQUEST -> shouldExtractData(resultCode, data) { viewModel.onReveiveDropOffAddressResult(it) }
    }
  }

  private fun shouldExtractData(resultCode: Int, data: Intent, resultOk: (Address) -> Unit) {
    if (resultCode == Activity.RESULT_OK)
      resultOk(data.getParcelableExtra<Address>(SearchAddressActivity.EXTRA_ADDRESS))
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
      it.observe(this, mapViewModel::pickupAddressChanged)
    }

    viewModel.navigateToPickupAddressSearch
        .observe(this, this::navigateToPickupAddressSearch)
    viewModel.navigateToDropOffAddressSearch
        .observe(this, this::navigateToDropOffAddressSearch)
  }

  private fun navigateToPickupAddressSearch(address: Address?) {
    navigator.navigateToAddressSearchWithAddress(this, PICKUP_ADDRESS_REQUEST, address)
  }

  private fun navigateToDropOffAddressSearch(address: Address?) {
    navigator.navigateToAddressSearchWithAddress(this, DROPOFF_ADDRESS_REQUEST, address)
  }

  private fun pickupAddressChanged(address: Address?) {
    addressPickupView.applyAddress(address)
  }

  companion object {
    val TAG: String = PickupDropoffFragment::class.java.canonicalName

    val PICKUP_ADDRESS_REQUEST = 1
    val DROPOFF_ADDRESS_REQUEST = 2

    fun newInstance(): PickupDropoffFragment {
      return PickupDropoffFragment()
    }
  }

}
