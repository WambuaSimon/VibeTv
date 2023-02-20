package com.vibetv.core.data.entities.movie_details

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vibetv.core.converters.GenreListConverter
import com.vibetv.core.converters.ProductionCompanyConverter
import com.vibetv.core.model.shared_models.Genre
import com.vibetv.core.model.movie_details.MovieDetailsResponse
import com.vibetv.core.model.shared_models.ProductionCompany

@Entity(tableName = "movie_details")
data class MovieDetailsResponseEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    @TypeConverters(GenreListConverter::class)
    val genres: List<Genre>,
    val homepage: String,
    val imdb_id: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    @TypeConverters(ProductionCompanyConverter::class)
    val production_companies: List<ProductionCompany>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    internal companion object {
        fun MovieDetailsResponse.toMovieDetailsEntity() = MovieDetailsResponseEntity(
            id = id,
            adult = adult,
            backdrop_path = backdrop_path,
            budget = budget,
            genres = genres,
            homepage = homepage,
            imdb_id = imdb_id,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            production_companies = production_companies,
            release_date = release_date,
            revenue = revenue,
            runtime = runtime,
            status = status,
            title = title,
            video = video,
            vote_average = vote_average,
            vote_count = vote_count

        )
    }
}
