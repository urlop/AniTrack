package com.anitrack.ruby.anitrack.network.models.genre

import android.os.Parcelable
import com.anitrack.ruby.anitrack.network.models.DataAnime
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseGenre(val data: List<Genre>?) : Parcelable

@Parcelize
data class Genre(
        val id: String?,
        val type: String?,
        val links: Links?,
        val attributes: Attributes?
) : Parcelable

@Parcelize
data class Attributes(
        val createdAt: String?,
        val updatedAt: String?,
        val name: String?,
        val slug: String?,
        val description: String?
) : Parcelable

@Parcelize
data class Links(
        val self: String?
) : Parcelable