package com.msmlabs.outcoding.data

import com.msmlabs.outcoding.domain.Either
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.domain.repository.CatRepository
import kotlinx.coroutines.flow.MutableStateFlow

class TestCatRepository : CatRepository {

    private val catListResponse = MutableStateFlow<Either<List<Cat>, Exception>?>(null)
    private val catDetailResponse = MutableStateFlow<Either<Cat, Exception>?>(null)

    override suspend fun getCatList(): Either<List<Cat>, Exception> {
        return catListResponse.value
            ?: throw IllegalStateException("Response not set for getCatList")
    }

    override suspend fun getCatById(id: String): Either<Cat, Exception> {
        return catDetailResponse.value
            ?: throw IllegalStateException("Response not set for getCatById")
    }

    fun setCatListResponse(response: Either<List<Cat>, Exception>) {
        catListResponse.value = response
    }

    fun setCatDetailResponse(response: Either<Cat, Exception>) {
        catDetailResponse.value = response
    }
}
