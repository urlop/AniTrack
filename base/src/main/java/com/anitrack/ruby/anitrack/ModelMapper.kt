package com.anitrack.ruby.anitrack

import com.anitrack.ruby.anitrack.data.source.local.models.Anime
import com.anitrack.ruby.anitrack.data.source.remote.models.Attributes
import com.anitrack.ruby.anitrack.data.source.remote.models.DataAnime

fun toAnime(dataAnime: DataAnime, attributes: Attributes) = Anime(
        id = dataAnime.id,
        type = dataAnime.type,
        createdAt = attributes.createdAt,
        updatedAt = attributes.updatedAt,
        slug = attributes.slug,
        synopsis = attributes.synopsis,
        coverImageTopOffset = attributes.coverImageTopOffset,
        //titles
        canonicalTitle = attributes.canonicalTitle,
        abbreviatedTitles = attributes.abbreviatedTitles,
        averageRating = attributes.averageRating,
        //ratingFrequencies
        userCount = attributes.userCount,
        favoritesCount = attributes.favoritesCount,
        startDate = attributes.startDate,
        endDate = attributes.endDate,
        popularityRank = attributes.popularityRank,
        ratingRank = attributes.ratingRank,
        ageRating = attributes.ageRating,
        ageRatingGuide = attributes.ageRatingGuide,
        subtype = attributes.subtype,
        status = attributes.status,
        tba = attributes.tba,
        //poster coverImage
        episodeCount = attributes.episodeCount,
        episodeLength = attributes.episodeLength,
        youtubeVideoId = attributes.youtubeVideoId,
        showType = attributes.showType,
        nsfw = attributes.nsfw
)

fun Attributes.toUserViewReflection() = with(::Anime) {
    val propertiesByName = Anime::class.members.associateBy { it.name }
    callBy(parameters.associate { parameter ->
        parameter to when (parameter.name) {
            Anime::ageRating.name -> ageRating
            else -> propertiesByName[parameter.name]
        }
    })
}