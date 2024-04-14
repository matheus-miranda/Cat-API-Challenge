package com.msmlabs.outcoding.data.di

import com.msmlabs.outcoding.data.network.CatRetrofitDataSource
import com.msmlabs.outcoding.data.repository.CatRepositoryImpl
import com.msmlabs.outcoding.domain.repository.CatRemoteDataSource
import com.msmlabs.outcoding.domain.repository.CatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRemoteDataSource(
        dataSource: CatRetrofitDataSource,
    ): CatRemoteDataSource

    @Binds
    abstract fun bindCatRepository(
        repository: CatRepositoryImpl,
    ): CatRepository
}
