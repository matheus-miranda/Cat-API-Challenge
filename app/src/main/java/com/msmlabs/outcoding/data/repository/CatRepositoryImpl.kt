package com.msmlabs.outcoding.data.repository

import com.msmlabs.outcoding.domain.Either
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.domain.repository.CatRemoteDataSource
import com.msmlabs.outcoding.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val remoteDataSource: CatRemoteDataSource,
) : CatRepository {

    override suspend fun getCatList(): Either<List<Cat>, Exception> {
        return remoteDataSource.getCatList()
    }

    override suspend fun getCatById(id: String): Either<Cat, Exception> {
        return remoteDataSource.getCatById(id)
    }
}
