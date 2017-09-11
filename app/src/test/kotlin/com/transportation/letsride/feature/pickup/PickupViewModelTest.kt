package com.transportation.letsride.feature.pickup

import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import com.transportation.letsride.feature.pickup.viewmodel.ViewState
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.transportation.letsride.data.Fabricator

@RunWith(JUnit4::class)
class PickupViewModelTest {

  @Rule @JvmField
  var instantExecutorRule = InstantTaskExecutorRule()

  lateinit var pickupViewModel: PickupViewModel

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    pickupViewModel = PickupViewModel()
  }

  @Test
  fun testViewStateMustBeInit() {
    assertThat(pickupViewModel.viewState.value, `is`(ViewState.INIT))
  }

  @Test
  fun testAddressesFilledMust() {
    pickupViewModel.pickupDropOffAddressFilled(Fabricator.hqPinPoint() to Fabricator.puertaDelSolPinPoint())
    assertThat(pickupViewModel.viewState.value, `is`(ViewState.ESTIMATE))
  }


}
