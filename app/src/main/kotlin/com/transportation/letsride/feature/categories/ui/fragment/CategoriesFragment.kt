package com.transportation.letsride.feature.categories.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.transportation.letsride.R
import com.transportation.letsride.common.ui.fragment.BaseFragment
import com.transportation.letsride.common.util.unsafeLazy
import com.transportation.letsride.feature.categories.viewmodel.CategoriesViewModel
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses

class CategoriesFragment : BaseFragment() {

  val categoriesViewModel: CategoriesViewModel by unsafeLazy({
    ViewModelProviders.of(this, viewModelFactory)
        .get(CategoriesViewModel::class.java)
  })

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_categories, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
//    listenData()
//    listenViews()
  }

  fun loadCategoriesWith(filledAddresses: FilledAddresses?) {
    categoriesViewModel.loadCategories(filledAddresses)
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
