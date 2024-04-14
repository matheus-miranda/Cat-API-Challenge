package com.msmlabs.outcoding.domain.repository

import com.msmlabs.outcoding.domain.Either
import com.msmlabs.outcoding.domain.model.Cat

interface CatRepository {
    suspend fun getCatList(): Either<List<Cat>, Exception>
    suspend fun getCatById(id: String): Either<Cat, Exception>
}
