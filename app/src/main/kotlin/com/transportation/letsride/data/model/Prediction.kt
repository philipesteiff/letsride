package com.transportation.letsride.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Prediction(
    val id: String,
    val description: String,
    @SerializedName("place_id") val placeId: String,
    val reference: String,
    val terms: List<Term> = listOf(),
    val types: List<String> = listOf(),
    @SerializedName("matched_substrings") val matchedSubstrings: List<MatchedSubstring> = listOf(),
    @SerializedName("structured_formatting") val structuredFormatting: StructuredFormatting = StructuredFormatting("")
) : AutoCompleteSuggestion, Parcelable {

  override fun getSubtitle() = structuredFormatting.secondaryText

  override fun getTitle() = structuredFormatting.mainText

  constructor(parcel: Parcel) : this(
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.createTypedArrayList(Term),
      parcel.createStringArrayList(),
      parcel.createTypedArrayList(MatchedSubstring),
      parcel.readParcelable(StructuredFormatting::class.java.classLoader))

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(id)
    parcel.writeString(description)
    parcel.writeString(placeId)
    parcel.writeString(reference)
    parcel.writeTypedList(terms)
    parcel.writeStringList(types)
    parcel.writeTypedList(matchedSubstrings)
    parcel.writeParcelable(structuredFormatting, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Prediction> {
    override fun createFromParcel(parcel: Parcel): Prediction {
      return Prediction(parcel)
    }

    override fun newArray(size: Int): Array<Prediction?> {
      return arrayOfNulls(size)
    }
  }

}
