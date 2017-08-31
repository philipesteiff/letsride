package com.transportation.letsride.data.api

import com.transportation.letsride.data.model.GeocodeResponse
import com.transportation.letsride.data.model.PlaceAutocompleteResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsApi {

  companion object {
    const val GOOGLE_MAPS_API_KEY = "AIzaSyAeC4hVs-Jq2eYILUXPvVYSodsBkRxyw_U"
  }

  @GET("/maps/api/place/autocomplete/json")
  fun getPlaceAutocomplete(
      @Query("key") key: String = GOOGLE_MAPS_API_KEY,
      @Query("input") input: String,
      @Query("offset") offset: Int? = null,
      @Query("location") location: String? = null,
      @Query("radius") radius: Int? = null,
      @Query("language") language: String? = null,
      @Query("types") types: String? = null,
      @Query("components") components: String? = null,
      @Query("strictbounds") strictBounds: Boolean? = null
  ): Single<PlaceAutocompleteResponse>

  @GET("/maps/api/geocode/json")
  fun getReverseGeocode(
      @Query("key") key: String = GOOGLE_MAPS_API_KEY,
      @Query("latlng") latLng: String? = null,
      @Query("place_id") placeId: String? = null
  ): Single<GeocodeResponse>

}
