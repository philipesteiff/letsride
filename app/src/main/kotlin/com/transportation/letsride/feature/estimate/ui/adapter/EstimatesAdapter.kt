package com.transportation.letsride.feature.estimate.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.adapter.BaseListAdapter
import com.transportation.letsride.common.ui.adapter.BaseViewHolder
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.data.model.Estimate
import kotlinx.android.synthetic.main.row_estimate.view.imgEstimateRowIcon
import kotlinx.android.synthetic.main.row_estimate.view.textEstimateRowEta
import kotlinx.android.synthetic.main.row_estimate.view.textEstimateRowName

class EstimatesAdapter(val context: Context) : BaseListAdapter<Estimate>() {

  val glideRequestManager: RequestManager by unsafeLazy { Glide.with(context) }

  val glideRequestOptions: RequestOptions = RequestOptions()
      .placeholder(R.drawable.ic_estimates_placeholder)
      .error(R.drawable.ic_estimates_placeholder)
      .circleCrop()


  override fun onBindViewHolder(holder: BaseViewHolder<Estimate>?, position: Int) {
    when (holder) {
      is SuggestionViewHolder -> holder.bind(get(position))
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Estimate> {
    val inflatedView = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
    return SuggestionViewHolder(inflatedView)
  }

  inner class SuggestionViewHolder(
      val view: View
  ) : BaseViewHolder<Estimate>(view) {

    override fun bind(element: Estimate) {

      element.vehicle.run {
        icons?.regular?.let { loadIcon(it).into(itemView.imgEstimateRowIcon) }
        itemView.textEstimateRowName.text = element.vehicle.shortName
        itemView.textEstimateRowEta.text = element.vehicle.eta?.formatted
      }

      element.vehicle
//        `@+id/textPlaceRowName`.text = element.vehicle.shortName
//        `@+id/textPlaceRowDescription`.text = element?.vehicle?.eta?.formatted
    }

    private fun loadIcon(url: String) = glideRequestManager
        .load(url)
        .apply(glideRequestOptions)
        .transition(DrawableTransitionOptions.withCrossFade())

  }

  companion object {
    val LAYOUT_ID = R.layout.row_estimate
  }
}
