package com.transportation.letsride.data.model

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
) : AutocompleteSuggestion {

  override fun getSubtitle() = structuredFormatting.secondaryText

  override fun getTitle() = structuredFormatting.mainText

}
