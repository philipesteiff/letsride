package com.transportation.letsride.feature.pickup

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.PositionAssertions.isCompletelyBelow
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.transportation.letsride.R
import com.transportation.letsride.data.Fabricator
import com.transportation.letsride.data.RepositoryMocks
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import com.transportation.letsride.feature.pickupdropoff.ui.widget.AddressView
import org.hamcrest.core.Is
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PickupActivityTest {

  @Rule @JvmField
  val activityRule: ActivityTestRule<PickupActivity> = ActivityTestRule(PickupActivity::class.java, true, false)

  val repositoryMocks = RepositoryMocks()

  @Test
  fun openApp_pinMustBeVisible() {
    launchWithDefaultState()

    onView(withId(R.id.viewPickupPin))
        .check(matches(isCompletelyDisplayed()))
    onView(withId(R.id.viewPickupPin))
        .check(matches(isCompletelyDisplayed()))
  }

  @Test
  fun openApp_pinMustBeBelowPickupDropOffWidget() {
    launchWithDefaultState()

    onView(withId(R.id.viewPickupPin))
        .check(isCompletelyBelow(withId(R.id.pickupDropOffAddressContainer)))
  }

  @Test
  fun openApp_buttonPickupMyLocationMustBeVisible() {
    launchWithDefaultState()

    onView(withId(R.id.buttonPickupMyLocation))
        .check(matches(isCompletelyDisplayed()))
  }

  @Test
  fun openApp_ButtonMyLocationMustBeBelowPickupDropOffWidget() {
    launchWithDefaultState()

    onView(withId(R.id.buttonPickupMyLocation))
        .check(isCompletelyBelow(withId(R.id.pickupDropOffAddressContainer)))
  }

  @Test
  fun openApp_ButtonMyLocationMustBeBelowPin() {
    launchWithDefaultState()

    onView(withId(R.id.buttonPickupMyLocation))
        .check(isCompletelyBelow(withId(R.id.viewPickupPin)))
  }

  @Test
  fun buttonMyLocationClick_PickupAddressMustBeFilled() {
    repositoryMocks.mockPuertaDelSolLocation()

    activityRule.launchActivity(null)

    repositoryMocks.mockHqAddressLocation()

    onView(withId(R.id.buttonPickupMyLocation))
        .perform(click())

    onView(ViewMatchers.withTagValue(Is.`is`(AddressView.PICKUP_ADDRESS_INPUT_TAG)))
        .check(matches(ViewMatchers.withText(Fabricator.hqPinPoint().address)))
  }

  private fun launchWithDefaultState() {
    repositoryMocks.mockHqAddressLocation()
    activityRule.launchActivity(null)
  }


}
