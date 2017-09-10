package com.transportation.letsride

import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
open class BaseInstrumentedTest {

  val app by lazy { InstrumentationRegistry.getTargetContext() as App }

  @Rule
  @JvmField
  val server = MockWebServer()


  @Before
  fun init() {
  }

  @After
  fun tearDown() {
    server.shutdown()
  }

}
