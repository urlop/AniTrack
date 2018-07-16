package com.anitrack.ruby.anitrack.network.models

import com.google.gson.annotations.SerializedName

data class AnimeCharacters(val links: Links?)

data class AnimeProductions(val links: Links?)

data class AnimeStaff(val links: Links?)

data class Attributes(val createdAt: String?, val updatedAt: String?, val slug: String?, val synopsis: String?, val coverImageTopOffset: Number?, val titles: Titles?, val canonicalTitle: String?, val abbreviatedTitles: List<String>?, val averageRating: String?, val ratingFrequencies: RatingFrequencies?, val userCount: Number?, val favoritesCount: Number?, val startDate: String?, val endDate: String?, val popularityRank: Number?, val ratingRank: Number?, val ageRating: String?, val ageRatingGuide: String?, val subtype: String?, val status: String?, val tba: String?, val posterImage: PosterImage?, val coverImage: CoverImage?, val episodeCount: Number?, val episodeLength: Number?, val youtubeVideoId: String?, val showType: String?, val nsfw: Boolean?)

data class BaseAnime(val data: List<DataAnime>?)

data class Castings(val links: Links?)

data class Categories(val links: Links?)

data class CoverImage(val tiny: String?, val small: String?, val large: String?, val original: String?, val meta: Meta?)

data class DataAnime(val id: String?, val type: String?, val links: Links?, val attributes: Attributes?, val relationships: Relationships?)

data class Dimensions(val tiny: Tiny?, val small: Small?, val medium: Medium?, val large: Large?)

data class Episodes(val links: Links?)

data class Genres(val links: Links?)

data class Installments(val links: Links?)

data class Large(val width: Number?, val height: Number?)

data class Links(val self: String?, val related: String?)

data class Mappings(val links: Links?)

data class MediaRelationships(val links: Links?)

data class Medium(val width: Number?, val height: Number?)

data class Meta(val dimensions: Dimensions?)

data class PosterImage(val tiny: String?, val small: String?, val medium: String?, val large: String?, val original: String?, val meta: Meta?)

data class RatingFrequencies(@SerializedName("2") val two: String?,@SerializedName("3") val three: String?, @SerializedName("4") val four: String?, @SerializedName("5") val five: String?, @SerializedName("6") val six: String?, @SerializedName("7") val seven: String?, @SerializedName("8") val eight: String?, @SerializedName("9") val nine: String?, @SerializedName("10") val ten: String?, @SerializedName("11") val eleven: String?, @SerializedName("12") val twelve: String?, @SerializedName("13") val thirteen: String?, @SerializedName("14") val fourteen: String?, @SerializedName("15") val fifteen: String?,@SerializedName("16")  val sixteen: String?, @SerializedName("17") val seventeen: String?, @SerializedName("18") val eighteen: String?, @SerializedName("19") val nineteen: String?, @SerializedName("20") val twenty: String?)

data class Relationships(val genres: Genres?, val categories: Categories?, val castings: Castings?, val installments: Installments?, val mappings: Mappings?, val reviews: Reviews?, val mediaRelationships: MediaRelationships?, val episodes: Episodes?, val streamingLinks: StreamingLinks?, val animeProductions: AnimeProductions?, val animeCharacters: AnimeCharacters?, val animeStaff: AnimeStaff?)

data class Reviews(val links: Links?)

data class Small(val width: Number?, val height: Number?)

data class StreamingLinks(val links: Links?)

data class Tiny(val width: Number?, val height: Number?)

data class Titles(@SerializedName("en") val english: String?, @SerializedName("en_jp") val englishJapan: String?, @SerializedName("ja_jp") val japanesseJapan: String?)