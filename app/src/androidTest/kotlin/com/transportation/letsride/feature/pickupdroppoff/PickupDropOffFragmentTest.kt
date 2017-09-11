package com.transportation.letsride.feature.pickupdroppoff

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.transportation.letsride.feature.PickupActivityRobot
import com.transportation.letsride.feature.pickup.ui.activity.PickupActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class PickupDropOffFragmentTest {

  @Rule @JvmField
  val activityRule: ActivityTestRule<PickupActivity> = ActivityTestRule(PickupActivity::class.java, true, false)

  val robot = PickupActivityRobot(activityRule)

  @Test
  fun openApp_addressesMustBeVisible() {
    robot.launch()
        .isAddressPickupDisplayed()
        .isAddressDropOffDisplayed()
  }

  @Test
  fun openApp_pickupAddressMustBeFilled() {
    robot.launch(PickupActivityRobot.MockedLocation.HQ)
        .isPickupAddressFilled(PickupActivityRobot.MockedLocation.HQ)
  }

  @Test
  fun openApp_dropOffAddressMustBeUnfilled() {
    robot.launch()
        .isDropOffAddressUnfilled()
  }

  @Test
  fun pickupAddressClick_shouldRetrievePickupAddressFilled() {
    robot.launch()
        .clickPickupAddressInput(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
        .checkPickupAddressInput(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
  }

  @Test
  fun pickupAddressClick_shouldRetrieveDropOffAddressFilled() {
    robot.launch()
        .mockEstimatesRequest()
        .clickDropOffAddressInput(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
        .checkDropOffAddressInput(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
  }

}
