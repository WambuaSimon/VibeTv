package com.vibetv.core.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vibetv.core.data.entities.AllMoviesResult
import com.vibetv.core.data.entities.GenreListEntity
import com.vibetv.core.data.entities.MovieByGenreEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    //Now Playing
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(playing: List<NowPlayingResultEntity>)

    @Query("SELECT * FROM now_playing")
    fun nowPlaying(): PagingSource< Int, NowPlayingResultEntity>

    @Query("SELECT * FROM now_playing")
    fun nowPlayingHome(): Flow<List<NowPlayingResultEntity>>

    @Query("DELETE FROM now_playing")
    suspend fun clearNowPlaying()

    @Transaction
    suspend fun replaceNowPlaying(nowPlayingResult: List<NowPlayingResultEntity>) {
        clearNowPlaying()
        insertAll(nowPlayingResult)
    }

    // Popular
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopular(popularResult: List<PopularResultEntity>)

    @Query("SELECT * FROM popular")
    fun popular(): Flow<List<PopularResultEntity>>

    @Query("DELETE FROM popular")
    suspend fun clearPopular()

    @Transaction
    suspend fun replacePopular(popularResult: List<PopularResultEntity>) {
        clearPopular()
        insertPopular(popularResult)
    }

    //Top rated
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRated(topRatedResult: List<TopRatedResultEntity>)

    @Query("SELECT * FROM top_rated")
    fun topRated(): Flow<List<TopRatedResultEntity>>

    @Query("DELETE FROM top_rated")
    suspend fun clearTopRated()

    @Transaction
    suspend fun replaceTopRated(topRatedResult: List<TopRatedResultEntity>) {
        clearTopRated()
        insertTopRated(topRatedResult)
    }

    //movie details
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetailsResponseEntity)

    @Query("SELECT *  FROM movie_details WHERE :id = id")
    fun movieDetails(id: Int): Flow<MovieDetailsResponseEntity>

    @Query("DELETE from movie_details")
    suspend fun clearMovieDetails()

    @Transaction
    suspend fun replaceMovieDetails(movieDetails: MovieDetailsResponseEntity) {
        clearMovieDetails()
        insertMovieDetails(movieDetails)
    }

    //Genre
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenreList(genreList: List<GenreListEntity>)

    @Query("SELECT *  FROM genre")
    fun genreList(): Flow<List<GenreListEntity>>

    @Query("DELETE from genre")
    suspend fun clearGenreList()

    @Transaction
    suspend fun replaceGenreList(genreList: List<GenreListEntity>) {
        clearGenreList()
        insertGenreList(genreList)
    }

    //all movies
    @Query("SELECT *  FROM popular, now_playing, top_rated")
    fun allMovies(): Flow<List<AllMoviesResult>>

    //Movie by genre
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieByGenre(movieByGenre: List<MovieByGenreEntity>)

    @Query("SELECT *  FROM movies_by_genre")
    fun movieByGenre(): Flow<List<MovieByGenreEntity>>

    @Query("DELETE from movies_by_genre")
    suspend fun clearMovieByGenre()

    @Transaction
    suspend fun replaceMovieByGenre(movieByGenre: List<MovieByGenreEntity>) {
        clearMovieByGenre()
        insertMovieByGenre(movieByGenre)
    }


}