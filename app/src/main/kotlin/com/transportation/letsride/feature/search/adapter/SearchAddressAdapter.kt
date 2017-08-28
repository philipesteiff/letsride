package com.transportation.letsride.feature.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.adapter.BaseListAdapter
import com.transportation.letsride.common.ui.adapter.BaseViewHolder
import com.transportation.letsride.data.model.AutocompleteSuggestion
import kotlinx.android.synthetic.main.row_suggestion_address.view.*

class SearchAddressAdapter(
    var onClick: (AutocompleteSuggestion) -> Unit = {}
) : BaseListAdapter<AutocompleteSuggestion>() {

  override fun onBindViewHolder(holder: BaseViewHolder<AutocompleteSuggestion>?, position: Int) {
    when (holder) {
      is SuggestionViewHolder -> holder.bind(get(position))
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AutocompleteSuggestion> {
    val inflatedView = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
    return SuggestionViewHolder(inflatedView)
  }

  inner class SuggestionViewHolder(
      val view: View
  ) : BaseViewHolder<AutocompleteSuggestion>(view) {

    override fun bind(element: AutocompleteSuggestion) {
      itemView.apply {
        placeRowName.text = element.getTitle()
        placeRowDescription.text = element.getSubtitle()
        setOnClickListener { onClick(element) }
      }
    }
  }

  companion object {
    val LAYOUT_ID = R.layout.row_suggestion_address
  }
}
