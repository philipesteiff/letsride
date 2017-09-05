package com.transportation.letsride.feature.pickupdropoff.ui.fragment

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
import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropOffViewModel
import com.transportation.letsride.feature.search.ui.activity.SearchAddressActivity
import kotlinx.android.synthetic.main.fragment_pickup_dropoff.addressDropOffView
import kotlinx.android.synthetic.main.fragment_pickup_dropoff.addressPickupView
import timber.log.Timber
import javax.inject.Inject

class PickupDropoffFragment : BaseFragment() {

  @Inject
  lateinit var navigator: Navigator

  val pickupViewModel: PickupViewModel by unsafeLazy {
    ViewModelProviders.of(activity, viewModelFactory)
        .get(PickupViewModel::class.java)
  }

  val mapViewModel: MapViewModel by unsafeLazy {
    ViewModelProviders.of(activity, viewModelFactory)
        .get(MapViewModel::class.java)
  }

  val pickupDropoffViewModel: PickupDropOffViewModel by unsafeLazy {
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

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      PICKUP_ADDRESS_REQUEST -> shouldExtractData(resultCode, data) { pickupDropoffViewModel.onReceivePickupAddressResult(it) }
      DROPOFF_ADDRESS_REQUEST -> shouldExtractData(resultCode, data) { pickupDropoffViewModel.onReceiveDropOffAddressResult(it) }
    }
  }

  private fun shouldExtractData(resultCode: Int, data: Intent?, resultOk: (Address) -> Unit) {
    if (resultCode == Activity.RESULT_OK)
      data?.getParcelableExtra<Address?>(SearchAddressActivity.EXTRA_ADDRESS)?.let(resultOk)
    else
      Timber.e("Failed to receive address.")
  }

  private fun listenViews() {
    addressPickupView.setOnClickListener { pickupDropoffViewModel.onPickupAddressClicked() }
    addressDropOffView.setOnClickListener { pickupDropoffViewModel.onDropoffAddressClicked() }
  }

  private fun listenData() {
    mapViewModel.mapCameraPosition
        .observe(this, pickupDropoffViewModel::newMapCameraPosition)

    pickupDropoffViewModel.pickupAddressChange
        .observe(this, this::pickupAddressChanged)

    pickupDropoffViewModel.adjustMapByPickupAddressLocation
        .observe(this, mapViewModel::moveToPickupAddressLocation)

    pickupDropoffViewModel.dropOffAddressChange
        .observe(this, this::dropOffAddressChanged)

    pickupDropoffViewModel.navigateToPickupAddressSearch
        .observe(this, this::navigateToPickupAddressSearch)

    pickupDropoffViewModel.navigateToDropOffAddressSearch
        .observe(this, this::navigateToDropOffAddressSearch)

    pickupDropoffViewModel.pickupDropOffAddressFilled.let {
      it.observe(this, mapViewModel::pickupDropOffAddressFilled)
      it.observe(this, pickupViewModel::pickupDropOffAddressFilled)
    }

  }

  private fun navigateToPickupAddressSearch(unit: Unit?) {
    navigator.navigateToAddressSearch(this, PICKUP_ADDRESS_REQUEST)
  }

  private fun navigateToDropOffAddressSearch(unit: Unit?) {
    navigator.navigateToAddressSearch(this, DROPOFF_ADDRESS_REQUEST)
  }

  private fun pickupAddressChanged(address: Address?) {
    addressPickupView.applyAddress(address)
  }

  private fun dropOffAddressChanged(address: Address?) {
    addressDropOffView.applyAddress(address)
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
