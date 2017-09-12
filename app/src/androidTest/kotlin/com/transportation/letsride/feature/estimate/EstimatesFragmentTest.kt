package com.transportation.letsride.feature.estimate

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
class EstimatesFragmentTest {

  @Rule @JvmField
  val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION)

  @Rule @JvmField
  val activityRule: ActivityTestRule<PickupActivity> = ActivityTestRule(PickupActivity::class.java, true, false)

  val robot = PickupActivityRobot(activityRule)

  @Test
  fun openApp_estimatesShouldNotBeVisible() {
    robot.launch()
        .isEstimatesNotVisible()
  }

  @Test
  fun filledAddresses_estimatesMustBeVisible() {
    robot.launch()
        .mockEstimatesRequest()
        .clickPickupAddressInput(PickupActivityRobot.MockedLocation.HQ)
        .clickDropOffAddressInput(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
        .isEstimatesVisible()
  }

  @Test
  fun filledAddresses_estimatesMustBeVisibleWithEmptyList() {
    robot.launch()
        .mockEstimatesRequestZeroResults()
        .clickPickupAddressInput(PickupActivityRobot.MockedLocation.HQ)
        .clickDropOffAddressInput(PickupActivityRobot.MockedLocation.PUERTA_DEL_SOL)
        .isEstimatesVisible()
  }

}
