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
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.feature.pickup.viewmodel.MapViewModel
import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropOffViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.PickupDropOffViewModelState
import com.transportation.letsride.feature.search.ui.activity.SearchAddressActivity
import kotlinx.android.synthetic.main.fragment_pickup_dropoff.addressDropOffView
import kotlinx.android.synthetic.main.fragment_pickup_dropoff.addressPickupView
import timber.log.Timber
import javax.inject.Inject

class PickupDropOffFragment : BaseFragment() {

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

  val pickupDropOffViewModel: PickupDropOffViewModel by unsafeLazy {
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

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    pickupDropOffViewModel.savedState(savedInstanceState)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      PICKUP_ADDRESS_REQUEST -> shouldExtractData(resultCode, data) { pickupDropOffViewModel.onReceivePickupAddressResult(it) }
      DROPOFF_ADDRESS_REQUEST -> shouldExtractData(resultCode, data) { pickupDropOffViewModel.onReceiveDropOffAddressResult(it) }
    }
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    outState?.putParcelable(PickupDropOffViewModelState.KEY_STATE, pickupDropOffViewModel.saveState())
    super.onSaveInstanceState(outState)
  }

  private fun shouldExtractData(resultCode: Int, data: Intent?, resultOk: (PinPoint) -> Unit) {
    if (resultCode == Activity.RESULT_OK)
      data?.getParcelableExtra<PinPoint?>(SearchAddressActivity.EXTRA_ADDRESS)?.let(resultOk)
    else
      Timber.e("Failed to receive address.")
  }

  private fun listenViews() {
    addressPickupView.setOnClickListener { pickupDropOffViewModel.onPickupAddressClicked() }
    addressDropOffView.setOnClickListener { pickupDropOffViewModel.onDropOffAddressClicked() }
  }

  private fun listenData() {
    mapViewModel.mapCameraPosition
        .observe(this, pickupDropOffViewModel::newMapCameraPosition)

    pickupDropOffViewModel.pickupAddressChange
        .observe(this, this::pickupAddressChanged)

    pickupDropOffViewModel.adjustMapByPickupAddressLocation
        .observe(this, mapViewModel::moveToPickupAddressLocation)

    pickupDropOffViewModel.dropOffAddressChange
        .observe(this, this::dropOffAddressChanged)

    pickupDropOffViewModel.navigateToPickupAddressSearch
        .observe(this, this::navigateToPickupAddressSearch)

    pickupDropOffViewModel.navigateToDropOffAddressSearch
        .observe(this, this::navigateToDropOffAddressSearch)

    pickupDropOffViewModel.pickupDropOffAddressFilled.let {
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

  private fun pickupAddressChanged(pinPoint: PinPoint?) {
    addressPickupView.applyAddress(pinPoint)
  }

  private fun dropOffAddressChanged(pinPoint: PinPoint?) {
    addressDropOffView.applyAddress(pinPoint)
  }

  companion object {
    val TAG: String = PickupDropOffFragment::class.java.canonicalName

    val PICKUP_ADDRESS_REQUEST = 1
    val DROPOFF_ADDRESS_REQUEST = 2

    fun newInstance(): PickupDropOffFragment {
      return PickupDropOffFragment()
    }
  }

}
