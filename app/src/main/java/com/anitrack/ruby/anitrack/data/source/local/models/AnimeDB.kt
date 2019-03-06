package com.anitrack.ruby.anitrack.data.source.local.models

import android.os.Parcelable
import androidx.room.Entity
//import com.anitrack.ruby.anitrack.data.source.remote.models.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "animes")
@Parcelize
data class Anime(val id: String?, val type: String?,
                 var genres: List<Genres>? = null,
                 val categories: List<Categories>? = null,
                 val castings: List<Castings>? = null,
                 val installments: List<Installments>? = null,
                 val mappings: List<Mappings>? = null,
                 val reviews: List<Reviews>? = null,
                 val mediaRelationships: List<MediaRelationships>? = null,
                 val episodes: List<Episodes>? = null,
                 var streamingLinks: List<StreamingLinks>? = null,
                 val animeProductions: List<AnimeProductions>? = null,
                 val animeCharacters: List<AnimeCharacters>? = null,
                 val animeStaff: List<AnimeStaff>? = null,

                 val createdAt: String?, val updatedAt: String?, val slug: String?, val synopsis: String?, val coverImageTopOffset: Number?,
                 val titles: List<Titles>? = null, val canonicalTitle: String?, val abbreviatedTitles: List<String>?, val averageRating: String?,
                 val ratingFrequencies: List<RatingFrequencies>? = null, val userCount: Number?, val favoritesCount: Number?, val startDate: String?, val endDate: String?, val popularityRank: Number?, val ratingRank: Number?, val ageRating: String?, val ageRatingGuide: String?, val subtype: String?, val status: String?, val tba: String?,
                 val posterImage: List<PosterImage>? = null,
                 val coverImage: List<CoverImage>? = null, val episodeCount: Number?, val episodeLength: Number?, val youtubeVideoId: String?, val showType: String?, val nsfw: Boolean?
                 ) : Parcelable { //IGNORABLE: Attributes (included inside), Links
    @IgnoredOnParcel
    val averageStar: Float?
        get() = (averageRating ?: "0").toFloat() * 5 / 100 //100 points to 5 stars
}

@Entity(tableName = "titles")
@Parcelize
data class Titles(@SerializedName("en") val english: String?, @SerializedName("en_jp") val englishJapan: String?, @SerializedName("en_us") val englishUniteStates: String?, @SerializedName("ja_jp") val japanesseJapan: String?) : Parcelable

@Entity(tableName = "rating_frequencies")
@Parcelize
data class RatingFrequencies(@SerializedName("2") val two: String?, @SerializedName("3") val three: String?, @SerializedName("4") val four: String?, @SerializedName("5") val five: String?, @SerializedName("6") val six: String?, @SerializedName("7") val seven: String?, @SerializedName("8") val eight: String?, @SerializedName("9") val nine: String?, @SerializedName("10") val ten: String?, @SerializedName("11") val eleven: String?, @SerializedName("12") val twelve: String?, @SerializedName("13") val thirteen: String?, @SerializedName("14") val fourteen: String?, @SerializedName("15") val fifteen: String?, @SerializedName("16") val sixteen: String?, @SerializedName("17") val seventeen: String?, @SerializedName("18") val eighteen: String?, @SerializedName("19") val nineteen: String?, @SerializedName("20") val twenty: String?) : Parcelable

@Entity(tableName = "poster_image")
@Parcelize
data class PosterImage(val tiny: String?, val small: String?, val medium: String?, val large: String?, val original: String?) : Parcelable //IGNORABLE: Meta

@Entity(tableName = "cover_image")
@Parcelize
data class CoverImage(val tiny: String?, val small: String?, val large: String?, val original: String?) : Parcelable //IGNORABLE: Meta


@Entity(tableName = "genres")
@Parcelize
data class Genres(@SerializedName("id") val id: String?, @SerializedName("name") val name: String?, @SerializedName("slug") val slug: String?) : Parcelable

@Entity(tableName = "categories")
@Parcelize
data class Categories(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "castings")
@Parcelize
data class Castings(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "installments")
@Parcelize
data class Installments(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "mappings")
@Parcelize
data class Mappings(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "reviews")
@Parcelize
data class Reviews(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "media_relationships")
@Parcelize
data class MediaRelationships(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "episodes")
@Parcelize
data class Episodes(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "streaming_links")
@Parcelize
data class StreamingLinks(@SerializedName("id") val id: String?, @SerializedName("url") val url: String?, @SerializedName("subs") val subs: List<String>?, @SerializedName("dubs") val dubs: List<String>?) : Parcelable

@Entity(tableName = "anime_productions")
@Parcelize
data class AnimeProductions(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "anime_characters")
@Parcelize
data class AnimeCharacters(@SerializedName("id") val id: String?) : Parcelable

@Entity(tableName = "anime_staff")
@Parcelize
data class AnimeStaff(@SerializedName("id") val id: String?) : Parcelable
