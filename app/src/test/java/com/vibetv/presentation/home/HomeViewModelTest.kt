package com.vibetv.presentation.home

import app.cash.turbine.test
import com.vibetv.common.utils.Resource
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.repository.MovieRepository
import com.vibetv.presentation.home.state.HomePageState
import com.vibetv.utils.MainDispatcherRule
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var movieRepo: MovieRepository

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val mockkRule = MockKRule(this)

    @Before
    fun setUp() {
        viewModel = HomeViewModel(movieRepo)
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(
            Resource.Loading, viewModel.state.value
        )
    }

    private val popularMovies = Resource.Success(
        listOf(
            PopularResultEntity(
                id = 123,
                adult = false,
                backdrop_path = "movie.jpg",
                overview = "Overview goes here",
                popularity = 4.0,
                poster_path = "movie.jpg",
                title = "Trending Movie",
                vote_average = 3.0,
                genreId = listOf(1, 2, 3),
                release_date = "2023-04-21"

            ),
            PopularResultEntity(
                id = 123,
                adult = false,
                backdrop_path = "movie.jpg",
                overview = "Overview goes here",
                popularity = 4.0,
                poster_path = "movie.jpg",
                title = "Popular Movie",
                vote_average = 3.0,
                genreId = listOf(1, 2, 3),
                release_date = "2023-04-21"

            )
        )
    )

    private fun mockMovieRepo(): MovieRepository {
        return mockk<MovieRepository>().apply {
            val popularMovies = MutableStateFlow(popularMovies)
            //val slot = slot<Map<String, List<PopularResultEntity>>>()
            every { getPopular() } returns popularMovies
        }
    }

    private fun mockViewModelDependencies(
        movieRepository: MovieRepository = mockMovieRepo()
    ): HomeViewModel {
        return HomeViewModel(
            repo = movieRepository
        )
    }

    @Test
    fun `Given failing data Then flow should emit error state`():Unit =
        runTest {
           viewModel.state.test {
              // awaitItem() shouldBe HomePageState()
           }
        }

    @Test
    fun `when home state is initialized then shows loading`() = runTest {
        viewModel.state.test {
            movieRepo.getTrendingMovies("day")

            assertTrue(awaitItem().result is HomePageState)
        }
    }

    private fun produceViewModel(viewState: HomePageState = HomePageState()): HomeViewModel {
        val viewModel = HomeViewModel(repo = movieRepo)
        return viewModel
    }


}