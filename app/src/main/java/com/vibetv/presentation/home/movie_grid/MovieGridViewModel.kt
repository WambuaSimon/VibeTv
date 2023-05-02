package com.vibetv.presentation.home.movie_grid

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vibetv.common.utils.Resource
import com.vibetv.core.api.VibeApi
import com.vibetv.core.data.AppDatabase
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TrendingEntity
import com.vibetv.core.paging.NowPlayingMovieMediator
import com.vibetv.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PAGE_SIZE = 20

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class MovieGridViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MovieRepository,
    private val db: AppDatabase,
    private val api: VibeApi
) : ViewModel() {
    private val title: String = savedStateHandle["title"]!!
    private val time: String = savedStateHandle["time"]!!

    val model: MovieGridModel = MovieGridModel(
        title = title
    )

    init {

        if (title.equals("Popular movies", ignoreCase = true)) {
            getPopular()
        }

        if (title.equals("Trending", ignoreCase = true)) {
            getTrending(time)
        }

    }

    private fun getTrending(timeWindow: String) {
        viewModelScope.launch {
            repo.getTrendingMovies(timeWindow).collect { trending ->
                when (trending) {
                    Resource.Loading -> Unit

                    is Resource.Error -> {
                        trending.mapError { buildTrendingState(it) }
                    }

                    is Resource.Success -> {
                        buildTrendingState(trending.result)
                    }

                }
            }
        }
    }

    fun getNowPlaying(): Flow<PagingData<NowPlayingResultEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                db.movieDao().nowPlaying()
            },
            remoteMediator = NowPlayingMovieMediator(
                api,
                db
            )
        ).flow

    /* private fun getNowPlaying() {
         viewModelScope.launch {
             repo.getNowPlaying().collect { playing ->
                 when (playing) {
                     Resource.Loading -> Unit

                     is Resource.Error -> {
                         playing.mapError { buildPlayingState(it) }
                     }


                     is Resource.Success -> {
                         buildPlayingState(playing.result)
                     }
                 }
             }
         }
     }*/

    private fun getPopular() {
        viewModelScope.launch {
            repo.getPopular().collect { popular ->
                when (popular) {
                    Resource.Loading -> Unit

                    is Resource.Error -> {
                        popular.mapError { buildPopularState(it) }
                    }

                    is Resource.Success -> {
                        buildPopularState(popular.result)
                    }

                }

            }
        }
    }

   /* private fun buildPlayingState(
        playing: List<NowPlayingResultEntity>
    ) {
        model.nowPlaying = playing
    }*/

    private fun buildPopularState(
        popular: List<PopularResultEntity>
    ) {
        model.popular = popular
    }

    private fun buildTrendingState(
        trending: List<TrendingEntity>
    ) {
        model.trending = trending
    }
}