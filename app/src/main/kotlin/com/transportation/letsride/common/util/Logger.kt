package com.transportation.letsride.common.util

import timber.log.Timber

interface Logger {

  var logger: Timber.Tree

  fun initLogger(logger: Timber.Tree) {
    Timber.plant(logger)
  }
}
