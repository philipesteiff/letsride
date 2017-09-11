package com.transportation.letsride.feature.pickup

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.mock
import com.transportation.letsride.data.Fabricator
import com.transportation.letsride.feature.pickup.viewmodel.PickupViewModel
import com.transportation.letsride.feature.pickup.viewmodel.ViewState
import com.transportation.letsride.feature.pickupdropoff.viewmodel.FilledAddresses
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

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
  fun testViewStateMustBeInitState() {
    assertThat(pickupViewModel.viewState.value, `is`(ViewState.INIT))
  }

  @Test
  fun testOnBackPressedMustReturnTrueWhenViewStateInit() {
    assertTrue(pickupViewModel.onBackPressed())
  }

  @Test
  fun testOnBackPressedMustReturnFalseWhenViewStateIsNotInit() {
    pickupViewModel.viewState.value = ViewState.ESTIMATE
    assertFalse(pickupViewModel.onBackPressed())
  }

  @Test
  fun testOnBackPressedMustChangeViewStateToInit() {
    pickupViewModel.viewState.value = ViewState.ESTIMATE
    pickupViewModel.onBackPressed()
    assertThat(pickupViewModel.viewState.value, `is`(ViewState.INIT))
  }

  @Test
  fun testAddressesFilledMust() {
    pickupViewModel.pickupDropOffAddressFilled(Fabricator.hqPinPoint() to Fabricator.puertaDelSolPinPoint())
    assertThat(pickupViewModel.viewState.value, `is`(ViewState.ESTIMATE))
  }

  @Test
  fun testWhenAddressFilledMustSetViewStateAndEstimates() {
    val viewStateObserver = mock<Observer<ViewState>>()
    pickupViewModel.viewState.observeForever(viewStateObserver)

    val estimatesObserver = mock<Observer<FilledAddresses>>()
    pickupViewModel.estimates.observeForever(estimatesObserver)

    val filledAddresses = Fabricator.hqPinPoint() to Fabricator.puertaDelSolPinPoint()
    pickupViewModel.pickupDropOffAddressFilled(filledAddresses)

    verify(viewStateObserver).onChanged(ViewState.ESTIMATE)
    verify(estimatesObserver).onChanged(filledAddresses)
  }

  @Test
  fun testEstimatesVisibilityMustBeChangedWhenViewStateChange() {
    val viewStateObserver = mock<Observer<ViewState>>()
    pickupViewModel.viewState.observeForever(viewStateObserver)

    val isEstimatesVisibleObserver = mock<Observer<Boolean>>()
    pickupViewModel.isEstimatesVisible.observeForever(isEstimatesVisibleObserver)

    verify(isEstimatesVisibleObserver).onChanged(false)

    pickupViewModel.viewState.value = ViewState.ESTIMATE
    verify(isEstimatesVisibleObserver).onChanged(true)
  }


}
