package com.msmlabs.outcoding.domain.model

data class Cat(
    val id: String,
    val imageUrl: String,
    val breeds: List<Breed>,
)

data class Breed(
    val name: String,
    val description: String,
    val temperament: String,
    val vcaHospitalsUrl: String?,
    val origin: String,
    val lifeSpan: String,
    val wikipediaUrl: String?,
)
