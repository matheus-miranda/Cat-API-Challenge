package com.msmlabs.outcoding.data.network

import com.msmlabs.outcoding.data.vo.CatVO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatApi {

    @GET("search")
    suspend fun getCatList(
        @Query("size") size: String = "thumb",
        @Query("mime_types") mimeTypes: String = "jpg",
        @Query("has_breeds") hasBreeds: String = "true",
        @Query("limit") limit: Int = 10,
    ): List<CatVO>

    @GET("{id}")
    suspend fun getCatById(
        @Path("id") id: String,
    ): CatVO
}
