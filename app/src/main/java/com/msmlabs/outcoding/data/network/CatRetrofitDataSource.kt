package com.msmlabs.outcoding.data.network

import com.msmlabs.outcoding.data.vo.mapper.toModel
import com.msmlabs.outcoding.domain.Either
import com.msmlabs.outcoding.domain.exception.NetworkException
import com.msmlabs.outcoding.domain.model.Cat
import com.msmlabs.outcoding.domain.repository.CatRemoteDataSource
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class CatRetrofitDataSource @Inject constructor(
    private val apiService: CatApi,
) : CatRemoteDataSource {

    override suspend fun getCatList(): Either<List<Cat>, Exception> {
        return try {
            val response = apiService.getCatList().map { it.toModel() }
            Either.Success(response)
        } catch (e: UnknownHostException) {
            Either.Failure(NetworkException.NetworkUnavailable)
        } catch (e: HttpException) {
            Either.Failure(NetworkException.ServerResponseError)
        } catch (e: Exception) {
            Either.Failure(NetworkException.GeneralException)
        }
    }

    override suspend fun getCatById(id: String): Either<Cat, Exception> {
        return try {
            val response = apiService.getCatById(id).toModel()
            Either.Success(response)
        } catch (e: UnknownHostException) {
            Either.Failure(NetworkException.NetworkUnavailable)
        } catch (e: HttpException) {
            Either.Failure(NetworkException.ServerResponseError)
        } catch (e: Exception) {
            Either.Failure(NetworkException.GeneralException)
        }
    }
}
