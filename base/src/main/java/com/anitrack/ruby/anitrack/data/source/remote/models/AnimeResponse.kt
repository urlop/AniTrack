package com.anitrack.ruby.anitrack.data.source.remote.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeCharactersWS(val links: LinksWS?) : Parcelable

@Parcelize
data class AnimeProductionsWS(val links: LinksWS?) : Parcelable

@Parcelize
data class AnimeStaffWS(val links: LinksWS?) : Parcelable

@Parcelize
data class AttributesWS(val createdAt: String?, val updatedAt: String?, val slug: String?, val synopsis: String?, val coverImageTopOffset: Number?, val titles: TitlesWS?, val canonicalTitle: String?, val abbreviatedTitles: List<String>?, val averageRating: String?, val ratingFrequencies: RatingFrequenciesWS?, val userCount: Number?, val favoritesCount: Number?, val startDate: String?, val endDate: String?, val popularityRank: Number?, val ratingRank: Number?, val ageRating: String?, val ageRatingGuide: String?, val subtype: String?, val status: String?, val tba: String?, val posterImage: PosterImageWS?, val coverImage: CoverImageWS?, val episodeCount: Number?, val episodeLength: Number?, val youtubeVideoId: String?, val showType: String?, val nsfw: Boolean?) : Parcelable {
    @IgnoredOnParcel
    val averageStar: Float?
            get() = (averageRating ?: "0").toFloat() * 5 / 100 //100 points to 5 stars
}

@Parcelize
data class BaseAnime(val data: List<AnimeWS>?) : Parcelable

@Parcelize
data class CastingsWS(val links: LinksWS?) : Parcelable

@Parcelize
data class CategoriesWS(val links: LinksWS?) : Parcelable

@Parcelize
data class CoverImageWS(val tiny: String?, val small: String?, val large: String?, val original: String?, val meta: MetaWS?) : Parcelable

@Parcelize
data class AnimeWS(val id: String?, val type: String?, val links: LinksWS?, val attributes: AttributesWS, val relationships: RelationshipsWS?) : Parcelable

@Parcelize
data class DimensionsWS(val tiny: TinyWS?, val small: SmallWS?, val medium: MediumWS?, val large: LargeWS?) : Parcelable

@Parcelize
data class EpisodesWS(val links: LinksWS?) : Parcelable

@Parcelize
data class GenresWS(val links: LinksWS?) : Parcelable

@Parcelize
data class InstallmentsWS(val links: LinksWS?) : Parcelable

@Parcelize
data class LargeWS(val width: Number?, val height: Number?) : Parcelable

@Parcelize
data class LinksWS(val self: String?, val related: String?) : Parcelable

@Parcelize
data class MappingsWS(val links: LinksWS?) : Parcelable

@Parcelize
data class MediaRelationshipsWS(val links: LinksWS?) : Parcelable

@Parcelize
data class MediumWS(val width: Number?, val height: Number?) : Parcelable

@Parcelize
data class MetaWS(val dimensions: DimensionsWS?) : Parcelable

@Parcelize
data class PosterImageWS(val tiny: String?, val small: String?, val medium: String?, val large: String?, val original: String?, val meta: MetaWS?) : Parcelable

@Parcelize
data class RatingFrequenciesWS(@SerializedName("2") val two: String?, @SerializedName("3") val three: String?, @SerializedName("4") val four: String?, @SerializedName("5") val five: String?, @SerializedName("6") val six: String?, @SerializedName("7") val seven: String?, @SerializedName("8") val eight: String?, @SerializedName("9") val nine: String?, @SerializedName("10") val ten: String?, @SerializedName("11") val eleven: String?, @SerializedName("12") val twelve: String?, @SerializedName("13") val thirteen: String?, @SerializedName("14") val fourteen: String?, @SerializedName("15") val fifteen: String?, @SerializedName("16")  val sixteen: String?, @SerializedName("17") val seventeen: String?, @SerializedName("18") val eighteen: String?, @SerializedName("19") val nineteen: String?, @SerializedName("20") val twenty: String?) : Parcelable

@Parcelize
data class RelationshipsWS(val genres: GenresWS?, val categories: CategoriesWS?, val castings: CastingsWS?, val installments: InstallmentsWS?, val mappings: MappingsWS?, val reviews: ReviewsWS?, val mediaRelationships: MediaRelationshipsWS?, val episodes: EpisodesWS?, val streamingLinks: StreamingLinksWS?, val animeProductions: AnimeProductionsWS?, val animeCharacters: AnimeCharactersWS?, val animeStaff: AnimeStaffWS?) : Parcelable

@Parcelize
data class ReviewsWS(val links: LinksWS?) : Parcelable

@Parcelize
data class SmallWS(val width: Number?, val height: Number?) : Parcelable

@Parcelize
data class StreamingLinksWS(val links: LinksWS?) : Parcelable

@Parcelize
data class TinyWS(val width: Number?, val height: Number?) : Parcelable

@Parcelize
data class TitlesWS(@SerializedName("en") val english: String?, @SerializedName("en_jp") val englishJapan: String?, @SerializedName("en_us") val englishUniteStates: String?, @SerializedName("ja_jp") val japanesseJapan: String?) : Parcelable