package com.transportation.letsride.common.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
fun <F : Fragment> FragmentActivity.findFragment(tag: String): F? {
  return (supportFragmentManager.findFragmentByTag(tag) as F?)
      .also { if (it == null) Timber.e("Fragment $it not found.") }
}

fun FragmentManager.commitTransactions(func: FragmentManager.(FragmentTransaction) -> Unit) {
  beginTransaction().apply { func(this) }.commit()
}

fun FragmentTransaction.attachFragment(fragment: Fragment, @IdRes content: Int, tag: String) {
  when {
    fragment.isDetached -> attach(fragment)
    !fragment.isAdded -> add(content, fragment, tag)
  }
}

fun FragmentTransaction.detachFragment(manager: FragmentManager, tag: String): Boolean {
  val fragment = manager.findFragmentByTag(tag)
  when {
    fragment != null && fragment.isAdded -> {
      detach(fragment)
      return true
    }
    else -> return false
  }
}
