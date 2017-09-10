package com.transportation.letsride.data

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import com.transportation.letsride.data.model.PinPoint
import com.transportation.letsride.feature.search.ui.activity.SearchAddressActivity


object IntentMock {

  fun mockSearchAddressActivityResult(pinPointReponse: PinPoint) = Instrumentation.ActivityResult(
      Activity.RESULT_OK,
      Intent().putExtra(SearchAddressActivity.EXTRA_ADDRESS, Fabricator.puertaDelSolPinPoint())
  )


}
