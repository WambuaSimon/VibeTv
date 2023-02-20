package com.vibetv.core.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app-database")
            .fallbackToDestructiveMigration()
            .build()
    }
}

@InstallIn(SingletonComponent::class)
@Module
internal class DatabaseDaoModule{
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Provides
    fun provideShowDao(db: AppDatabase) = db.showDao()

    @Provides
    fun seasonsDao(db: AppDatabase) = db.seasonsDao()

    @Provides
    fun provideMovieRemoteKeyDao(db: AppDatabase) = db.movieRemoteKeyDao()
}