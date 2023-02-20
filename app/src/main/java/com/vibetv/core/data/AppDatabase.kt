package com.vibetv.core.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.vibetv.core.converters.CreatedByConverter
import com.vibetv.core.converters.CrewConverter
import com.vibetv.core.converters.EpisodeConverter
import com.vibetv.core.converters.GenreIdConverter
import com.vibetv.core.converters.GenreListConverter
import com.vibetv.core.converters.GuestConverter
import com.vibetv.core.converters.ListStringConverter
import com.vibetv.core.converters.NetworksConverter
import com.vibetv.core.converters.ProductionCompanyConverter
import com.vibetv.core.converters.SeasonsConverter
import com.vibetv.core.data.dao.MovieDao
import com.vibetv.core.data.dao.MovieRemoteKeyDao
import com.vibetv.core.data.dao.SeasonsDao
import com.vibetv.core.data.dao.ShowDao
import com.vibetv.core.data.entities.GenreListEntity
import com.vibetv.core.data.entities.MovieByGenreEntity
import com.vibetv.core.data.entities.MovieRemoteKeyEntity
import com.vibetv.core.data.entities.NowPlayingResultEntity
import com.vibetv.core.data.entities.PopularResultEntity
import com.vibetv.core.data.entities.season.SeasonDetailsEntity
import com.vibetv.core.data.entities.TopRatedResultEntity
import com.vibetv.core.data.entities.movie_details.MovieDetailsResponseEntity
import com.vibetv.core.data.entities.season.EpisodeEntity
import com.vibetv.core.data.entities.show_details.ShowDetailsResponseEntity
import com.vibetv.core.data.entities.shows.AiringTodayEntity
import com.vibetv.core.data.entities.shows.LatestShowEntity
import com.vibetv.core.data.entities.shows.PopularShowsEntity
import com.vibetv.core.data.entities.shows.TopRatedShowsEntity

@Database(
    entities = [
        NowPlayingResultEntity::class,
        TopRatedResultEntity::class,
        PopularResultEntity::class,
        MovieDetailsResponseEntity::class,
        GenreListEntity::class,
        AiringTodayEntity::class,
        LatestShowEntity::class,
        PopularShowsEntity::class,
        TopRatedShowsEntity::class,
        ShowDetailsResponseEntity::class,
        SeasonDetailsEntity::class,
        MovieByGenreEntity::class,
        EpisodeEntity::class,
        MovieRemoteKeyEntity::class,


    ],
    version = 17,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10),
        AutoMigration(from = 10, to = 11),
        AutoMigration(from = 11, to = 12),
        AutoMigration(from = 12, to = 13, spec = AppDatabase.To13AutoMigrationSpec::class),
        AutoMigration(from = 13, to = 14),
        AutoMigration(from = 14, to = 15),
        AutoMigration(from = 15, to = 16),
        AutoMigration(from = 16, to = 17),
    ]
)

@TypeConverters(
    ProductionCompanyConverter::class,
    GenreListConverter::class,
    GenreIdConverter::class,
    ListStringConverter::class,
    ProductionCompanyConverter::class,
    CreatedByConverter::class,
    NetworksConverter::class,
    SeasonsConverter::class,
    CrewConverter::class,
    EpisodeConverter::class,
    GuestConverter::class,

    )
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun showDao(): ShowDao
    abstract fun seasonsDao(): SeasonsDao
    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao

    @DeleteTable(tableName = "episode_item")
    class To13AutoMigrationSpec : AutoMigrationSpec

}