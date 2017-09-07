package com.transportation.letsride.feature.estimate.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.common.util.observe
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.data.model.Estimate
import com.transportation.letsride.feature.estimate.ui.adapter.EstimatesAdapter
import com.transportation.letsride.feature.estimate.viewmodel.EstimatesViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses
import kotlinx.android.synthetic.main.fragment_estimates.recyclerEstimates

class EstimatesFragment : BaseFragment() {

  val estimatesViewModel: EstimatesViewModel by unsafeLazy({
    ViewModelProviders.of(this, viewModelFactory)
        .get(EstimatesViewModel::class.java)
  })

  val estimatesAdapter by unsafeLazy { EstimatesAdapter(context) }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_estimates, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recyclerEstimates.apply {
      this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
      this.adapter = estimatesAdapter
    }

    listenData()
  }

  private fun listenData() {
    estimatesViewModel.estimates
        .observe(this, this::onReceiveEstimates)
  }

  fun loadEstimatesWith(filledAddresses: FilledAddresses?) {
    estimatesViewModel.loadEstimates(filledAddresses)
  }

  fun onReceiveEstimates(estimates: List<Estimate>?) {
    estimatesAdapter.clear()
    estimatesAdapter.addAll(estimates.orEmpty())
  }

  companion object {
    val TAG: String = EstimatesFragment::class.java.canonicalName

    fun newInstance(): EstimatesFragment {
      return EstimatesFragment().apply {
        val extra = Bundle()
        arguments = extra
      }
    }
  }

}
