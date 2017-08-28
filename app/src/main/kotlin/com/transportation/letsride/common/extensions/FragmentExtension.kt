package com.transportation.letsride.common.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

@Suppress("UNCHECKED_CAST")
fun <F : Fragment> FragmentActivity.findFragment(tag: String): F? {
  return supportFragmentManager.findFragmentByTag(tag) as F?
}

fun FragmentManager.commitNowTransactions(func: FragmentManager.(FragmentTransaction) -> Unit) {
  beginTransaction().apply { func(this) }.commitNow()
}

fun FragmentTransaction.attachFragment(fragment: Fragment, @IdRes content: Int, tag: String) {
  when {
    fragment.isDetached -> attach(fragment)
    !fragment.isAdded -> add(content, fragment, tag)
  }
}
