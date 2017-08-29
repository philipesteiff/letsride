package com.transportation.letsride.data.model

data class AutocompleteOptions(
    var location: Pair<Double, Double>? = null,
    val radius: Int? = null,
    val language: String? = null,
    val components: List<String> = listOf()
)

