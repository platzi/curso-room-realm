package com.julianvelandia.bizorder.data.di

import com.julianvelandia.bizorder.data.OrdersRepositoryImpl
import com.julianvelandia.bizorder.data.local.LocalDataStorage
import com.julianvelandia.bizorder.data.remote.RemoteDataStorage
import com.julianvelandia.bizorder.domain.OrdersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideOrdersRepository(
        remoteDataStorage: RemoteDataStorage,
        localDataStorage: LocalDataStorage
    ): OrdersRepository = OrdersRepositoryImpl(remoteDataStorage, localDataStorage)

}