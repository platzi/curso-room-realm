package com.julianvelandia.bizorder.data.di

import com.julianvelandia.bizorder.data.local.realm.OrderObject
import com.julianvelandia.bizorder.data.local.realm.PreOrderObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder(
            setOf(OrderObject::class, PreOrderObject::class)
        )
            .name("realm_database.db")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    @Provides
    @Singleton
    fun provideRealmInstance(config: RealmConfiguration): Realm {
        return Realm.open(config)
    }
}