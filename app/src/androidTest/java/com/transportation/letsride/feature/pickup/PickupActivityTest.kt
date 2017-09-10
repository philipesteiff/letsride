package com.transportation.letsride.feature.pickup

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.PositionAssertions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.transportation.letsride.BaseInstrumentedTest
import com.transportation.letsride.R
import com.transportation.letsride.common.extensions.findFragment
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import com.transportation.letsride.feature.pickupdropoff.ui.fragment.PickupDropOffFragment
import org.junit.Rule
import org.junit.Test

class PickupActivityTest : BaseInstrumentedTest() {

  @Rule @JvmField
  val activityRule: ActivityTestRule<PickupActivity> = ActivityTestRule(PickupActivity::class.java, true, true)

  @Test
  fun openApp_pinMustBeVisible() {
    onView(withId(R.id.imagePickupMapMarker))
        .check(matches(isCompletelyDisplayed()))
    onView(withId(R.id.viewPickupCenterPoint))
        .check(matches(isCompletelyDisplayed()))
  }

  @Test
  fun openApp_pinMustBeBelowPickupDropOffWidget() {
    onView(withId(R.id.imagePickupMapMarker))
        .check(isCompletelyBelow(withId(R.id.pickupDropOffAddressContainer)))
  }

  @Test
  fun openApp_buttonPickupMyLocationMustBeVisible() {
    onView(withId(R.id.buttonPickupMyLocation))
        .check(matches(isCompletelyDisplayed()))
  }

  @Test
  fun openApp_ButtonMyLocationMustBeBelowPickupDropOffWidget() {
    onView(withId(R.id.buttonPickupMyLocation))
        .check(isCompletelyBelow(withId(R.id.pickupDropOffAddressContainer)))
  }

  @Test
  fun openApp_ButtonMyLocationMustBeBelowPin() {
    onView(withId(R.id.buttonPickupMyLocation))
        .check(isCompletelyBelow(withId(R.id.imagePickupMapMarker)))
  }

  @Test
  fun openApp_blah() {
    Thread.sleep(20000)
    activityRule.activity.findFragment<PickupDropOffFragment>(PickupDropOffFragment.TAG)
        .pickupDropoffViewModel.on
  }


}
