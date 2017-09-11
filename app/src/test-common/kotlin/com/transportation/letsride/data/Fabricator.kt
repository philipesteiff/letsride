package com.transportation.letsride.data

import android.location.Location
import android.location.LocationManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.whenever
import com.transportation.letsride.data.model.Estimate
import com.transportation.letsride.data.model.PinPoint
import org.mockito.Mockito.mock

object Fabricator {

  fun mockLocation(
      provider: String = LocationManager.GPS_PROVIDER,
      latitude: Double = -23.0,
      longitude: Double = -46.0,
      accuracy: Float = 15f
  ): Location = mock(Location::class.java).apply {
    whenever(this.accuracy).thenReturn(accuracy)
    whenever(this.latitude).thenReturn(latitude)
    whenever(this.longitude).thenReturn(longitude)
    whenever(this.provider).thenReturn(provider)
  }

  fun hqLocation() = mockLocation(
      latitude = 40.4456588, longitude = -3.7086401 /* Cabify HQ */
  )

  fun hqPinPoint() = PinPoint(
      name = "Calle Marqués de Lema",
      address = "Calle Marqués de Lema, 7, 28003 Madrid, Spain",
      number = "s/n",
      city = "Madrid",
      country = "Spain",
      latitude = hqLocation().latitude,
      longitude = hqLocation().longitude
  )

  fun puertaDelSolLocation() = mockLocation(
      latitude = 40.4169514, longitude = -3.7057172
  )

  fun puertaDelSolPinPoint() = PinPoint(
      name = "Puerta del Sol",
      address = "Plaza de la Puerta del Sol, s/n, 28013 Madrid, Espanha",
      number = "s/n",
      city = "Madrid",
      country = "Spain",
      latitude = puertaDelSolLocation().latitude,
      longitude = puertaDelSolLocation().longitude
  )

  //region Estimates
  private val estimatesJson = """
  [
    { "vehicle_type":{ "_id":"c96d7175756797d1c2e99bab9d93ca61", "name":"Cabify Executive", "short_name":"Executive ", "description":"Luxury cars, Mercedes E-Class or similar. 4 passenger max", "icon":"executive", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/executive_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":2000, "currency":"EUR", "currency_symbol":"€", "price_formatted":"20.00 €" },
    { "vehicle_type":{ "_id":"08803037ede65ea6103fff6cc6443bf0", "name":"Cabify Lite", "short_name":"Lite", "description":"The standard option, with all the usual perks. 4 passengers max", "icon":"lite", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/lite_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":969, "currency":"EUR", "currency_symbol":"€", "price_formatted":"9.69 €" },
    { "vehicle_type":{ "_id":"1660b8de89dd1441c66408754e992b1d", "name":"Cabify BBVA", "short_name":"BBVA", "description":"BBVA", "icon":"snow", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/snow_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":1000, "currency":"EUR", "currency_symbol":"€", "price_formatted":"10.00 €" },
    { "vehicle_type":{ "_id":"3fd4294640f9ed06df29bc22bacde0ad", "name":"Cabify Lite 1", "short_name":"Lite 1 ", "description":"VW Passat or similar. 4 pax. Reservations only.", "icon":"group_golf", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/group_golf_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":541, "currency":"EUR", "currency_symbol":"€", "price_formatted":"5.41 €" },
    { "vehicle_type":{ "_id":"49992c5cac39f61acd2e3ac571fa8ff6", "name":"Halloween", "short_name":"Halloween", "description":"Indica el tipo de silla en el mensaje. Grupo 0 solo con reserva y suplemento de 5€", "icon":"lite", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/lite_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":705, "currency":"EUR", "currency_symbol":"€", "price_formatted":"7.05 €" },
    { "vehicle_type":{ "_id":"83a26b83e7cf49444ea438a8adfb3d60", "name":"Debito", "short_name":"Debito", "description":"Debito", "icon":"pet", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/pet_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":705, "currency":"EUR", "currency_symbol":"€", "price_formatted":"7.05 €" },
    { "vehicle_type":{ "_id":"a0a7664cdb120036b899e16f9484f889", "name":"Voom ", "short_name":"Voom ", "description":"Voom without dest", "icon":"cabion", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/cabion_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":null, "currency":null, "currency_symbol":null, "price_formatted":"N/A" },
    { "vehicle_type":{ "_id":"a0a7664cdb120036b899e16f94850231", "name":"Voom dest", "short_name":"Voom dest", "description":"Voom destination mandatory", "icon":"cabion", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/cabion_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":null, "currency":null, "currency_symbol":null, "price_formatted":"N/A" },
    { "vehicle_type":{ "_id":"aa8d29ba13f78a73643227d0d1990108", "name":"delivery", "short_name":"delivery", "description":"Our motorbike courier service for deliveries across the city.", "icon":"delivery", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/delivery_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":625, "currency":"EUR", "currency_symbol":"€", "price_formatted":"6.25 €" },
    { "vehicle_type":{ "_id":"af45ed76d3d048543cc5b256114fb10a", "name":"TaxiCash", "short_name":"Tash", "description":"Taxi Cash", "icon":"baby", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/baby_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":null, "currency":null, "currency_symbol":null, "price_formatted":"N/A" },
    { "vehicle_type":{ "_id":"c52ce29f50438491f8d6e55d5259dd40", "name":"Rickshaw", "short_name":"rickshaw", "description":"Share a ride with private drivers", "icon":"rickshaw", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/rickshaw_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":787, "currency":"EUR", "currency_symbol":"€", "price_formatted":"7.87 €" },
    { "vehicle_type":{ "_id":"4fb92398ff5472e61b19bf41b40cd0f6", "name":"Taxi", "short_name":"Taxi", "description":"Taxi me!", "icon":"taxi", "icons":{ "regular":"https://test.cabify.com/images/icons/vehicle_type/taxi_54.png" }, "eta":{ "min":0, "max":999999, "low_availability":true, "formatted":"10-20 min" } }, "total_price":null, "currency":null, "currency_symbol":null, "price_formatted":"N/A" }
  ]
  """
  private val listType = object : TypeToken<List<Estimate>>() {}.type
  fun estimatesHqToPuertaDelSol(): List<Estimate> = Gson().fromJson<List<Estimate>>(estimatesJson, listType)
  //endregion



}
