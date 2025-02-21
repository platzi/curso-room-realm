package com.julianvelandia.bizorder.data.di

import android.app.Application
import androidx.room.Room
import com.julianvelandia.bizorder.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, NAME_DATABASE)
            .setQueryExecutor(Executors.newSingleThreadExecutor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providesOrderDao(appDatabase: AppDatabase) = appDatabase.orderDao()
    @Provides
    fun providesPreOrderDao(appDatabase: AppDatabase) = appDatabase.preOrderDao()

    companion object {
        private const val NAME_DATABASE = "room_database.db"
    }



}