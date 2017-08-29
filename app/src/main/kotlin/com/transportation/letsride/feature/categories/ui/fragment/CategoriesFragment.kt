package com.transportation.letsride.feature.categories.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment

class CategoriesFragment : Fragment() {

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
