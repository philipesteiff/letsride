package com.transportation.letsride.feature

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.PositionAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.transportation.letsride.R
import com.transportation.letsride.data.Fabricator
import com.transportation.letsride.data.IntentMock
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import com.transportation.letsride.feature.pickupdropoff.ui.widget.AddressView
import com.transportation.letsride.feature.search.ui.activity.SearchAddressActivity
import com.transportation.letsride.util.RepositoryMocksRuled
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not

class PickupActivityRobot(val activityRule: ActivityTestRule<PickupActivity>) {

  enum class MockedLocation { HQ, PUERTA_DEL_SOL }

  val repositoryMocks = RepositoryMocksRuled()

  fun launch(mockedLocation: MockedLocation = MockedLocation.HQ) = apply {
    when (mockedLocation) {
      PickupActivityRobot.MockedLocation.HQ -> repositoryMocks.mockHqAddressLocation()
      PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL -> repositoryMocks.mockPuertaDelSolLocation()
    }
    activityRule.launchActivity(null)
  }

  fun isPinDisplayed() = apply {
    onView(withId(R.id.viewPickupPin))
        .check(matches(isCompletelyDisplayed()))
  }

  fun isPinBelowPickupDropOffWidget() = apply {
    onView(withId(R.id.viewPickupPin))
        .check(PositionAssertions.isCompletelyBelow(withId(R.id.pickupDropOffAddressContainer)))
  }

  fun isMyLocationButtonDisplayed() = apply {
    onView(withId(R.id.buttonPickupMyLocation))
        .check(matches(isCompletelyDisplayed()))
  }

  fun isMyLocationButtonBelowPickupDroOffWidget() = apply {
    onView(withId(R.id.buttonPickupMyLocation))
        .check(PositionAssertions.isCompletelyBelow(withId(R.id.pickupDropOffAddressContainer)))
  }

  fun clickMyLocationButton() = apply {
    onView(withId(R.id.buttonPickupMyLocation))
        .perform(click())
  }

  fun checkPickupAddressInput(mockedLocation: MockedLocation) = apply {
    val addressInput = retrieveAddressStringByMockedLocation(mockedLocation)

    onView(ViewMatchers.withTagValue(`is`(AddressView.PICKUP_ADDRESS_INPUT_TAG)))
        .check(matches(withText(addressInput)))
  }

  fun checkDropOffAddressInput(mockedLocation: MockedLocation) = apply {
    val addressInput = retrieveAddressStringByMockedLocation(mockedLocation)

    onView(ViewMatchers.withTagValue(`is`(AddressView.DROPOFF_ADDRESS_INPUT_TAG)))
        .check(matches(withText(addressInput)))
  }

  fun isMyLocationButtonBelowPin() = apply {
    onView(withId(R.id.buttonPickupMyLocation))
        .check(PositionAssertions.isCompletelyBelow(withId(R.id.viewPickupPin)))
  }

  fun isAddressPickupDisplayed() = apply {
    onView(withId(R.id.addressPickupView))
        .check(matches(isCompletelyDisplayed()))
  }

  fun isAddressDropOffDisplayed() = apply {
    onView(withId(R.id.addressDropOffView))
        .check(matches(isCompletelyDisplayed()))
  }

  fun isPickupAddressFilled(mockedLocation: MockedLocation) = apply {
    val addressInput = retrieveAddressStringByMockedLocation(mockedLocation)
    onView(withTagValue(`is`(AddressView.PICKUP_ADDRESS_INPUT_TAG)))
        .check(matches(withText(addressInput)))
  }

  fun isDropOffAddressUnfilled() = apply {
    onView(withTagValue(`is`(AddressView.DROPOFF_ADDRESS_INPUT_TAG)))
        .check(matches(withText("")))
  }

  fun clickPickupAddressInput(mockedLocation: MockedLocation) = apply {
    val pinPoint = retrievePinPointByMockedLocation(mockedLocation)

    Intents.init()
    Intents.intending(IntentMatchers.hasComponent(SearchAddressActivity::class.java.name))
        .respondWith(IntentMock.mockSearchAddressActivityResult(pinPoint))

    onView(withTagValue(`is`(AddressView.PICKUP_ADDRESS_INPUT_TAG)))
        .perform(click())

    Intents.intended(IntentMatchers.hasComponent(SearchAddressActivity::class.java.name))
    Intents.release()
  }

  fun clickDropOffAddressInput(mockedLocation: MockedLocation) = apply {
    val pinPoint = retrievePinPointByMockedLocation(mockedLocation)

    Intents.init()
    Intents.intending(IntentMatchers.hasComponent(SearchAddressActivity::class.java.name))
        .respondWith(IntentMock.mockSearchAddressActivityResult(pinPoint))

    onView(withTagValue(`is`(AddressView.DROPOFF_ADDRESS_INPUT_TAG)))
        .perform(click())

    Intents.intended(IntentMatchers.hasComponent(SearchAddressActivity::class.java.name))
    Intents.release()
  }

  fun isEstimatesNotVisible() = apply {
    onView(withId(R.id.estimatesContainer))
        .check(matches(not(isCompletelyDisplayed())))
  }

  fun mockEstimatesRequest() = apply {
    repositoryMocks.mockEstimatesHqToPuertaDelSol()
  }

  fun mockEstimatesRequestZeroResults() = apply {
    repositoryMocks.mockEstimatesHqToPuertaDelSolZeroResults()
  }

  fun isEstimatesVisible() = apply {
    onView(withId(R.id.estimatesContainer))
        .check(matches((isCompletelyDisplayed())))
  }

  private fun retrievePinPointByMockedLocation(mockedLocation: MockedLocation): PinPoint {
    return when (mockedLocation) {
      MockedLocation.HQ -> Fabricator.hqPinPoint()
      MockedLocation.PUERTA_DEL_SOL -> Fabricator.puertaDelSolPinPoint()
    }
  }

  private fun retrieveAddressStringByMockedLocation(mockedLocation: MockedLocation): String {
    return when (mockedLocation) {
      MockedLocation.HQ -> Fabricator.hqPinPoint().address
      MockedLocation.PUERTA_DEL_SOL -> Fabricator.puertaDelSolPinPoint().address
    }
  }

}
