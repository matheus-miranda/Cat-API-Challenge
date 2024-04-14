package com.msmlabs.outcoding.data.vo

import com.google.gson.annotations.SerializedName

data class CatVO(
    @SerializedName("id") val id: String,
    @SerializedName("url") val imageUrl: String,
    @SerializedName("breeds") val breeds: List<BreedVO>,
)

data class BreedVO(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("temperament") val temperament: String,
    @SerializedName("vcahospitals_url") val vcaHospitalsUrl: String?,
    @SerializedName("origin") val origin: String,
    @SerializedName("life_span") val lifeSpan: String,
    @SerializedName("wikipedia_url") val wikipediaUrl: String?,
)
