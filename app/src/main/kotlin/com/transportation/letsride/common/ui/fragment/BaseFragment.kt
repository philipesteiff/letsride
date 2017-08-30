package com.transportation.letsride.common.ui.fragment

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProvider
import com.transportation.letsride.common.di.Injectable
import javax.inject.Inject

open class BaseFragment : LifecycleFragment(), Injectable {
  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
}
