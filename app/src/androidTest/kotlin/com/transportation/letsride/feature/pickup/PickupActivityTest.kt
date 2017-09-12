package com.transportation.letsride.feature.pickup

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import com.transportation.letsride.feature.PickupActivityRobot
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PickupActivityTest {

  @Rule @JvmField
  val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)

  @Rule @JvmField
  val activityRule: ActivityTestRule<PickupActivity> = ActivityTestRule(PickupActivity::class.java, true, false)

  val robot = PickupActivityRobot(activityRule)

  @Test
  fun openApp_pinMustBeVisible() {
    robot.launch()
        .isPinDisplayed()
  }

  @Test
  fun openApp_pinMustBeBelowPickupDropOffWidget() {
    robot.launch()
        .isPinBelowPickupDropOffWidget()
  }

  @Test
  fun openApp_buttonPickupMyLocationMustBeVisible() {
    robot.launch()
        .isMyLocationButtonDisplayed()
  }

  @Test
  fun openApp_ButtonMyLocationMustBeBelowPickupDropOffWidget() {
    robot.launch()
        .isMyLocationButtonBelowPickupDroOffWidget()
  }

  @Test
  fun openApp_ButtonMyLocationMustBeBelowPin() {
    robot.launch()
        .isMyLocationButtonBelowPin()
  }

  @Test
  fun buttonMyLocationClick_PickupAddressMustBeFilled() {
    robot.launch(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
        .clickMyLocationButton()
        .checkPickupAddressInput(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
  }

}
