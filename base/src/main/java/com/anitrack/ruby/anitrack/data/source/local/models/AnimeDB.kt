package com.anitrack.ruby.anitrack.data.source.local.models

import android.os.Parcelable
import androidx.room.Entity
//import com.anitrack.ruby.anitrack.data.source.remote.models.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataAnime(val id: String?, val type: String?, val links: Links?,
                     val attributes: Attributes,
                     val genres: Genres?,
                     val categories: Categories?,
                     val castings: Castings?,
                     val installments: Installments?,
                     val mappings: Mappings?,
                     val reviews: Reviews?,
                     val mediaRelationships: MediaRelationships?,
                     val episodes: Episodes?,
                     val streamingLinks: StreamingLinks?,
                     val animeProductions: AnimeProductions?,
                     val animeCharacters: AnimeCharacters?,
                     val animeStaff: AnimeStaff?) : Parcelable

@Parcelize
data class Attributes(val createdAt: String?, val updatedAt: String?, val slug: String?, val synopsis: String?, val coverImageTopOffset: Number?,
                      val titles: Titles?, val canonicalTitle: String?, val abbreviatedTitles: List<String>?, val averageRating: String?,
                      val ratingFrequencies: RatingFrequencies?, val userCount: Number?, val favoritesCount: Number?, val startDate: String?, val endDate: String?, val popularityRank: Number?, val ratingRank: Number?, val ageRating: String?, val ageRatingGuide: String?, val subtype: String?, val status: String?, val tba: String?,
                      val posterImage: PosterImage?,
                      val coverImage: CoverImage?, val episodeCount: Number?, val episodeLength: Number?, val youtubeVideoId: String?, val showType: String?, val nsfw: Boolean?) : Parcelable {
    @IgnoredOnParcel
    val averageStar: Float?
        get() = (averageRating ?: "0").toFloat() * 5 / 100 //100 points to 5 stars
}

@Parcelize
data class Titles(@SerializedName("en") val english: String?, @SerializedName("en_jp") val englishJapan: String?, @SerializedName("en_us") val englishUniteStates: String?, @SerializedName("ja_jp") val japanesseJapan: String?) : Parcelable

@Parcelize
data class RatingFrequencies(@SerializedName("2") val two: String?, @SerializedName("3") val three: String?, @SerializedName("4") val four: String?, @SerializedName("5") val five: String?, @SerializedName("6") val six: String?, @SerializedName("7") val seven: String?, @SerializedName("8") val eight: String?, @SerializedName("9") val nine: String?, @SerializedName("10") val ten: String?, @SerializedName("11") val eleven: String?, @SerializedName("12") val twelve: String?, @SerializedName("13") val thirteen: String?, @SerializedName("14") val fourteen: String?, @SerializedName("15") val fifteen: String?, @SerializedName("16")  val sixteen: String?, @SerializedName("17") val seventeen: String?, @SerializedName("18") val eighteen: String?, @SerializedName("19") val nineteen: String?, @SerializedName("20") val twenty: String?) : Parcelable

@Parcelize
data class PosterImage(val tiny: String?, val small: String?, val medium: String?, val large: String?, val original: String?) : Parcelable //IGNORABLE: Meta

@Parcelize
data class CoverImage(val tiny: String?, val small: String?, val large: String?, val original: String?) : Parcelable //IGNORABLE: Meta


@Parcelize
data class Links(val self: String?, val related: String?) : Parcelable


@Parcelize
data class Genres(@SerializedName("id") val id: String?, @SerializedName("name") val name: String?, @SerializedName("slug") val slug: String?) : Parcelable
@Parcelize
data class Categories(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class Castings(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class Installments(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class Mappings(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class Reviews(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class MediaRelationships(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class Episodes(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class StreamingLinks(@SerializedName("id") val id: String?, @SerializedName("url") val url: String?, @SerializedName("subs") val subs: List<String>?, @SerializedName("dubs") val dubs: List<String>?) : Parcelable
@Parcelize
data class AnimeProductions(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class AnimeCharacters(@SerializedName("id") val id: String?) : Parcelable
@Parcelize
data class AnimeStaff(@SerializedName("id") val id: String?) : Parcelable
