package com.vibetv.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vibetv.core.data.dao.MovieDao
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.core.paging.TrendingMoviesRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object PagingModule {
    @OptIn(ExperimentalPagingApi::class)
    @ViewModelScoped
    @Provides
    internal fun provideTrendingPager(
        remoteMediator: TrendingMoviesRemoteMediator,
        moviesDao: MovieDao
    ): Pager<Int, TrendingEntity> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20),
        remoteMediator = remoteMediator
    ) { moviesDao.trending() }
}