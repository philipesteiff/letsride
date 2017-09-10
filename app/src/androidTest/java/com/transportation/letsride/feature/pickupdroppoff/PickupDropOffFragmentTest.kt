package com.transportation.letsride.feature.pickupdroppoff

import android.app.Activity.RESULT_OK
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withTagValue
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.transportation.letsride.R
import com.transportation.letsride.data.Fabricator
import com.transportation.letsride.data.RepositoryMocks
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import com.transportation.letsride.feature.pickupdropoff.ui.widget.AddressView
import com.transportation.letsride.feature.search.ui.activity.SearchAddressActivity
import org.hamcrest.core.Is
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import com.transportation.letsride.data.IntentMock


@RunWith(AndroidJUnit4::class)
@LargeTest
class PickupDropOffFragmentTest {

  @Rule @JvmField
  val activityRule: IntentsTestRule<PickupActivity> = IntentsTestRule(PickupActivity::class.java, true, false)

  val repositoryMocks = RepositoryMocks()

  @Test
  fun openApp_addressesMustBeVisible() {
    repositoryMocks.mockHqAddressLocation()

    activityRule.launchActivity(null)

    onView(withId(R.id.addressPickupView))
        .check(matches(isCompletelyDisplayed()))
    onView(withId(R.id.addressDropOffView))
        .check(matches(isCompletelyDisplayed()))
  }

  @Test
  fun openApp_pickupAddressMustBeFilled() {
    repositoryMocks.mockHqAddressLocation()

    activityRule.launchActivity(null)

    onView(withTagValue(Is.`is`(AddressView.PICKUP_ADDRESS_INPUT_TAG)))
        .check(matches(withText(Fabricator.hqPinPoint().address)))
  }

  @Test
  fun openApp_dropOffAddressMustBeUnfilled() {
    repositoryMocks.mockHqAddressLocation()

    activityRule.launchActivity(null)

    onView(withTagValue(Is.`is`(AddressView.DROPOFF_ADDRESS_INPUT_TAG)))
        .check(matches(withText("")))
  }

  @Test
  fun pickupAddressClick_shouldRetrievePickupAddressFilled() {
    repositoryMocks.mockHqAddressLocation()

    activityRule.launchActivity(null)

    Intents.intending(hasComponent(SearchAddressActivity::class.java.name))
        .respondWith(IntentMock.mockSearchAddressActivityResult(Fabricator.puertaDelSolPinPoint()))

    onView(withTagValue(Is.`is`(AddressView.PICKUP_ADDRESS_INPUT_TAG)))
        .perform(click())
        .check(matches(withText(Fabricator.puertaDelSolPinPoint().address)))

    Intents.intended(IntentMatchers.hasComponent(SearchAddressActivity::class.java.name))
  }

  @Test
  fun pickupAddressClick_shouldRetrieveDropOffAddressFilled() {
    repositoryMocks.mockHqAddressLocation()

    activityRule.launchActivity(null)

    Intents.intending(hasComponent(SearchAddressActivity::class.java.name))
        .respondWith(IntentMock.mockSearchAddressActivityResult(Fabricator.puertaDelSolPinPoint()))

    onView(withTagValue(Is.`is`(AddressView.DROPOFF_ADDRESS_INPUT_TAG)))
        .perform(click())
        .check(matches(withText(Fabricator.puertaDelSolPinPoint().address)))

    Intents.intended(IntentMatchers.hasComponent(SearchAddressActivity::class.java.name))
  }

}
