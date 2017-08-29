package com.transportation.letsride.data.model

import android.os.Parcel
import android.os.Parcelable

data class Term(
    val value: String,
    val offset: Int
) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readInt())

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(value)
    parcel.writeInt(offset)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Term> {
    override fun createFromParcel(parcel: Parcel): Term {
      return Term(parcel)
    }

    override fun newArray(size: Int): Array<Term?> {
      return arrayOfNulls(size)
    }
  }
}
