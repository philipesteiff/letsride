package com.transportation.letsride.util

import com.transportation.letsride.EspressoDaggerMockRule
import com.transportation.letsride.data.RepositoryMocks
import org.junit.Rule


class RepositoryMocksRuled : RepositoryMocks() {

  @Rule @JvmField
  val rule = EspressoDaggerMockRule().run { initMocks(this@RepositoryMocksRuled) }

}
