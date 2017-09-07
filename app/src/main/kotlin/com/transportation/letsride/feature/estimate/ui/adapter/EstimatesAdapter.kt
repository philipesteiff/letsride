package com.transportation.letsride.feature.estimate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.adapter.BaseListAdapter
import com.transportation.letsride.common.ui.adapter.BaseViewHolder
import com.transportation.letsride.data.model.AutoCompleteSuggestion
import com.transportation.letsride.data.model.Estimate
import kotlinx.android.synthetic.main.row_suggestion_address.view.placeRowDescription
import kotlinx.android.synthetic.main.row_suggestion_address.view.placeRowName

class EstimatesAdapter : BaseListAdapter<Estimate>() {

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
      itemView.apply {
        placeRowName.text = element.vehicle.shortName
        placeRowDescription.text = element?.eta?.formatted.orEmpty()
      }
    }
  }

  companion object {
    val LAYOUT_ID = R.layout.row_suggestion_address
  }
}
