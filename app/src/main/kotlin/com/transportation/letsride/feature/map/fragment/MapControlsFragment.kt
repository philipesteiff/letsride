package com.transportation.letsride.feature.map.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment

class MapControlsFragment : BaseFragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_map_controls, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    presenter.bindView(this)
  }


  companion object {
    val TAG: String = MapControlsFragment::class.java.canonicalName

    fun newInstance(): MapControlsFragment {
      return MapControlsFragment()
    }
  }

}
