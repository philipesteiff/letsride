package com.transportation.letsride.data.model

import android.os.Parcel
import android.os.Parcelable

data class MatchedSubstring(
    val offset: Int,
    val length: Int
) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readInt(),
      parcel.readInt())

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(offset)
    parcel.writeInt(length)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<MatchedSubstring> {
    override fun createFromParcel(parcel: Parcel): MatchedSubstring {
      return MatchedSubstring(parcel)
    }

    override fun newArray(size: Int): Array<MatchedSubstring?> {
      return arrayOfNulls(size)
    }
  }
}
