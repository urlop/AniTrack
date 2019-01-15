package com.anitrack.ruby.anitrack.data.source

import com.anitrack.ruby.anitrack.data.source.local.models.*
import com.anitrack.ruby.anitrack.data.source.remote.models.*
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.GenreWS

fun animeToDb (anime : AnimeWS) : Anime {
    return Anime(anime.id, anime.type,
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            anime.attributes.createdAt, anime.attributes.updatedAt, anime.attributes.slug, anime.attributes.synopsis, anime.attributes.coverImageTopOffset,
            anime.attributes.titles?.let { titleToDb(it) }, anime.attributes.canonicalTitle, anime.attributes.abbreviatedTitles, anime.attributes.averageRating,
            anime.attributes.ratingFrequencies?.let { ratingToDb(it) }, anime.attributes.userCount, anime.attributes.favoritesCount, anime.attributes.startDate, anime.attributes.endDate, anime.attributes.popularityRank, anime.attributes.ratingRank, anime.attributes.ageRating, anime.attributes.ageRatingGuide, anime.attributes.subtype, anime.attributes.status, anime.attributes.tba,
            anime.attributes.posterImage?.let { posterImageToDb(it) },
            anime.attributes.coverImage?.let { coverImageToDb(it) } , anime.attributes.episodeCount, anime.attributes.episodeLength, anime.attributes.youtubeVideoId, anime.attributes.showType, anime.attributes.nsfw)
}

fun genresListToDb (genres : List<GenreWS>) : List<Genres> {
    var result = ArrayList<Genres>()
    for (item in genres) {
        result.add(genreToDb(item))
    }
    return result
}

fun genreToDb (genre : GenreWS) : Genres {
    return Genres(genre.id, genre.attributes?.name, genre.attributes?.slug)
}

fun titleToDb (titles : TitlesWS) : Titles {
    return Titles(titles.english, titles.englishJapan, titles.englishUniteStates, titles.japanesseJapan)
}

fun ratingToDb (rating : RatingFrequenciesWS) : RatingFrequencies {
    return RatingFrequencies(rating.two, rating.three, rating.four, rating.five, rating.six, rating.seven, rating.eight, rating.nine, rating.ten, rating.eleven, rating.twelve, rating.thirteen, rating.fourteen, rating.fifteen, rating.sixteen, rating.seventeen, rating.eighteen, rating.nineteen, rating.twenty)
}

fun posterImageToDb (posterImage : PosterImageWS) : PosterImage {
    return PosterImage(posterImage.tiny, posterImage.small, posterImage.medium, posterImage.large, posterImage.original)
}

fun coverImageToDb (coverImage : CoverImageWS) : CoverImage {
    return CoverImage(coverImage.tiny, coverImage.small, coverImage.large, coverImage.original)
}