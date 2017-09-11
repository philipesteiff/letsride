package com.transportation.letsride.feature.estimate.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.adapter.BaseListAdapter
import com.transportation.letsride.common.ui.adapter.BaseViewHolder
import com.transportation.letsride.data.model.Estimate
import kotlinx.android.synthetic.main.row_estimate.view.*

class EstimatesAdapter(val context: Context) : BaseListAdapter<Estimate>() {

  val iconSize = context.resources.getDimension(R.dimen.ic_estimates_size).toInt()

  override fun onBindViewHolder(holder: BaseViewHolder<Estimate>?, position: Int) {
    when (holder) {
      is EstimatesViewHolder -> holder.bind(get(position))
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Estimate> {
    val inflatedView = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
    return EstimatesViewHolder(inflatedView)
  }

  inner class EstimatesViewHolder(
      val view: View
  ) : BaseViewHolder<Estimate>(view) {

    override fun bind(element: Estimate) {
      element.vehicle.run {
        icons?.regular?.let { loadIcon(it).into(itemView.imgEstimateRowIcon) }
        itemView.textEstimateRowName.text = element.vehicle.shortName
        itemView.textEstimateRowEta.text = element.vehicle.eta?.formatted
        itemView.textEstimateRowPrice.text = element.formattedPrice.orEmpty()
      }
    }

    private fun loadIcon(url: String) = Picasso.with(context)
        .load(url)
        .resize(iconSize, iconSize)
        .error(R.drawable.ic_estimates_placeholder)
        .placeholder(R.drawable.ic_estimates_placeholder)

  }

  companion object {
    val LAYOUT_ID = R.layout.row_estimate
  }
}
