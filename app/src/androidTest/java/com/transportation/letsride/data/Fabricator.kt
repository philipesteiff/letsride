package com.transportation.letsride.data

import android.location.Location
import com.transportation.letsride.data.model.PinPoint


object Fabricator {

  fun hqLocation() = Location("mock").apply {
    latitude = 40.4456588; longitude = -3.7086401 /* Cabify HQ */
  }

  fun hqPinPoint() = PinPoint(
      name = "Calle Marqués de Lema",
      address = "Calle Marqués de Lema, 7, 28003 Madrid, Spain",
      number = "s/n",
      city = "Madrid",
      country = "Spain",
      latitude = hqLocation().latitude,
      longitude = hqLocation().longitude
  )

  fun puertaDelSolLocation() = Location("mock").apply {
    latitude = 40.4169514; longitude = -3.7057172
  }

  fun puertaDelSolPinPoint() = PinPoint(
      name = "Puerta del Sol",
      address = "Plaza de la Puerta del Sol, s/n, 28013 Madrid, Espanha",
      number = "s/n",
      city = "Madrid",
      country = "Spain",
      latitude = puertaDelSolLocation().latitude,
      longitude = puertaDelSolLocation().longitude
  )


}
