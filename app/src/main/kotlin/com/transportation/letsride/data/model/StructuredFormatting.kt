package com.transportation.letsride.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class StructuredFormatting(
    @SerializedName("main_text") val mainText: String,
    @SerializedName("main_text_matched_substrings")
    val mainTextMatchedSubstrings: List<MatchedSubstring> = listOf(),
    @SerializedName("secondary_text") val secondaryText: String = ""
) : Parcelable {

  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.createTypedArrayList(MatchedSubstring),
      parcel.readString())

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(mainText)
    parcel.writeTypedList(mainTextMatchedSubstrings)
    parcel.writeString(secondaryText)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<StructuredFormatting> {
    override fun createFromParcel(parcel: Parcel): StructuredFormatting {
      return StructuredFormatting(parcel)
    }

    override fun newArray(size: Int): Array<StructuredFormatting?> {
      return arrayOfNulls(size)
    }
  }
}
