package com.vibetv.core.api

import com.vibetv.core.model.genre.GenreResult
import com.vibetv.core.model.movie_details.MovieDetailsResponse
import com.vibetv.core.model.movie_response.MovieResponse
import com.vibetv.core.model.movie_response.MovieResults
import com.vibetv.core.model.movie_response.Paged
import com.vibetv.core.model.season_details.SeasonDetails
import com.vibetv.core.model.shows.EpisodeItem
import com.vibetv.core.model.shows.ShowResponse
import com.vibetv.core.model.shows.show_details.ShowDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VibeApi {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("page") page: Int,
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun nowPlaying(): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(): VibeCloudResponse<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(): VibeCloudResponse<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): VibeCloudResponse<MovieDetailsResponse>

    @GET("genre/movie/list")
    suspend fun getGenreList(): VibeCloudResponse<GenreResult>

    @GET("tv/airing_today")
    suspend fun getShowsAiringToday(): VibeCloudResponse<ShowResponse>

    @GET("tv/latest")
    suspend fun getLatestShows(): VibeCloudResponse<ShowResponse>

    @GET("tv/popular")
    suspend fun getPopularShows(): VibeCloudResponse<ShowResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedShows(): VibeCloudResponse<ShowResponse>

    @GET("tv/{id}")
    suspend fun getShowDetails(@Path("id") id: Int): VibeCloudResponse<ShowDetailsResponse>

    @GET("tv/{tvId}/season/{seasonNumber}")
    suspend fun getSeasonDetails(
        @Path("tvId") tvId: Int,
        @Path("seasonNumber") seasonNumber: Int,
    ): VibeCloudResponse<SeasonDetails>

    @GET("tv/{tvId}/season/{seasonNumber}/episode/{episodeNumber}")
    suspend fun getEpisodeItem(
        @Path("tvId") tvId: Int,
        @Path("seasonNumber") seasonNumber: Int,
        @Path("episodeNumber") episodeNumber: Int,
    ): VibeCloudResponse<EpisodeItem>

    @GET("discover/movie")
    suspend fun getMovieByGenre(@Query("with_genres") genreId: Int? = null): VibeCloudResponse<MovieResponse>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") mediaType:String,
        @Path("time_window") timeWindow:String,
    ): Paged<List<MovieResults>>

    @GET("trending/movie/{time_window}")
    suspend fun getTrending(
        @Path("time_window") timeWindow:String,
    ): Paged<List<MovieResults>>
}