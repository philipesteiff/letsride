package com.transportation.letsride.feature.categories.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R

class CategoriesFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_categories, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    listenData()
//    listenViews()
  }

  companion object {
    val TAG: String = CategoriesFragment::class.java.canonicalName

    fun newInstance(): CategoriesFragment {
      return CategoriesFragment().apply {
        val extra = Bundle()
        arguments = extra
      }
    }
  }

}
