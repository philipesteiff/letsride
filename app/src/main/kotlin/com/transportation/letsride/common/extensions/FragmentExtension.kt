package com.transportation.letsride.common.extensions

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

fun FragmentActivity.findFragment(tag: String): Fragment? {
  return supportFragmentManager.findFragmentByTag(tag)
}

fun FragmentManager.commitTransactions(func: FragmentManager.(FragmentTransaction) -> Unit) {
  beginTransaction().apply { func(this) }.commitNow()
}

fun FragmentTransaction.attachFragment(fragment: Fragment, @IdRes content: Int, tag: String) {
  when {
    fragment.isDetached -> attach(fragment)
    !fragment.isAdded -> add(content, fragment, tag)
  }
}
