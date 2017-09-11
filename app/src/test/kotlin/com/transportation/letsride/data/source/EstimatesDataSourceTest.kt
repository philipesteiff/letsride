package com.transportation.letsride.data.source

import com.nhaarman.mockito_kotlin.whenever
import com.transportation.letsride.data.api.JourneyApi
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
  fun testNullPinPointsMustReturnEmptyList() {
//    whenever(journeyApi.estimate())
    estimatesDataSource.estimates(null, null).test()
  }

}
