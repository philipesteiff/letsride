package com.transportation.letsride.setup

import com.transportation.letsride.App

class TestApp : App() {

  override fun inject(app: App) {
    DaggerTestApplicationComponent.builder()
        .create(this)
        .inject(this)
  }
}
