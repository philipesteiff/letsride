package com.transportation.letsride.data.model

import android.location.Address
import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.transportation.letsride.common.extensions.getFullAddressOrThoroughfareOrEmpty
import com.transportation.letsride.common.extensions.getThoroughfareOrFullAddressOrEmpty

data class PinPoint(
    val name: String,
    val address: String,
    val number: String = "s/n",
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable, AutoCompleteSuggestion {

  constructor(address: Address) : this(
      name = address.getThoroughfareOrFullAddressOrEmpty(),
      address = address.getFullAddressOrThoroughfareOrEmpty(),
      city = address.locality ?: address.subAdminArea.orEmpty(),
      country = address.countryName.orEmpty(),
      latitude = address.latitude,
      longitude = address.longitude
  )

  override fun getTitle() = name

  override fun getSubtitle() = address

  fun getLatLng() = LatLng(latitude, longitude)

  constructor(source: Parcel) : this(
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readString(),
      source.readDouble(),
      source.readDouble()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeString(name)
    writeString(address)
    writeString(number)
    writeString(city)
    writeString(country)
    writeDouble(latitude)
    writeDouble(longitude)
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<PinPoint> = object : Parcelable.Creator<PinPoint> {
      override fun createFromParcel(source: Parcel): PinPoint = PinPoint(source)
      override fun newArray(size: Int): Array<PinPoint?> = arrayOfNulls(size)
    }
  }
}
