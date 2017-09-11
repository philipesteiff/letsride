package com.transportation.letsride.data.source

import com.nhaarman.mockito_kotlin.whenever
import com.transportation.letsride.data.Fabricator
import com.transportation.letsride.data.api.JourneyApi
import com.transportation.letsride.data.model.JourneyBuilder
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class EstimatesDataSourceTest {

  @Mock
  lateinit var journeyApi: JourneyApi

  lateinit var estimatesDataSource: EstimatesDataSource

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    estimatesDataSource = EstimatesDataSource(journeyApi)
  }

  @Test
  fun testWithJourneyMustReturnEstimates() {
    val pickupPinPoint = Fabricator.hqPinPoint()
    val dropOffPinPoint = Fabricator.puertaDelSolPinPoint()
    val journeyEstimate = JourneyBuilder.buildJourneyEstimate(pickupPinPoint, dropOffPinPoint)
    val estimatesHqToPuertaDelSol = Fabricator.estimatesHqToPuertaDelSol()
    whenever(journeyApi.estimate(journeyEstimate)).thenReturn(Single.just(estimatesHqToPuertaDelSol))
    estimatesDataSource.estimates(pickupPinPoint, dropOffPinPoint)
        .test()
        .assertValue(estimatesHqToPuertaDelSol)
        .assertComplete()
  }

}
