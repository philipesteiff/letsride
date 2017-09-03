package com.transportation.letsride.data.model

import android.os.Parcel
import android.os.Parcelable

data class Address(
    val name: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readDouble(),
      parcel.readDouble())

  fun isValid() = true

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(name)
    parcel.writeString(city)
    parcel.writeString(country)
    parcel.writeDouble(latitude)
    parcel.writeDouble(longitude)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Address> {
    override fun createFromParcel(parcel: Parcel): Address {
      return Address(parcel)
    }

    override fun newArray(size: Int): Array<Address?> {
      return arrayOfNulls(size)
    }
  }

}
