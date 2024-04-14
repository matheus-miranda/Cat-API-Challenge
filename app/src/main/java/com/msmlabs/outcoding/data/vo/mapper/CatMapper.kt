package com.msmlabs.outcoding.data.vo.mapper

import com.msmlabs.outcoding.data.vo.BreedVO
import com.msmlabs.outcoding.data.vo.CatVO
import com.msmlabs.outcoding.domain.model.Breed
import com.msmlabs.outcoding.domain.model.Cat

fun CatVO.toModel() = Cat(
    id = this.id, imageUrl = this.imageUrl, breeds = this.breeds.map { it.toModel() }
)

fun BreedVO.toModel() = Breed(
    name = this.name,
    description = this.description,
    temperament = this.temperament,
    vcaHospitalsUrl = this.vcaHospitalsUrl,
    origin = this.origin,
    lifeSpan = this.lifeSpan,
    wikipediaUrl = this.wikipediaUrl
)
