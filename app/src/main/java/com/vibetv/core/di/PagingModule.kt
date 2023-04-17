package com.vibetv.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vibetv.core.data.dao.MovieDao
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.paging.MoviesRemoteMediator
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
    internal fun provideNowPlayingPager(
        remoteMediator: MoviesRemoteMediator,
        moviesDao: MovieDao
    ): Pager<Int, NowPlayingResultEntity> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 10),
        remoteMediator = remoteMediator
    ){ moviesDao.nowPlaying() }
}