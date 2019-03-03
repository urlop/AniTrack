package com.anitrack.ruby.anitrack.data.source.remote.models.streaming

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseStreaming(val data: List<Streaming>?) : Parcelable

@Parcelize
data class Streaming(
        val id: String?,
        val type: String?,
        val links: Links?,
        val attributes: Attributes?
) : Parcelable

@Parcelize
data class Attributes(
        val createdAt: String?,
        val updatedAt: String?,
        val url: String?,
        val subs: List<String>?,
        val dubs: List<String>?
) : Parcelable

@Parcelize
data class Links(
        val self: String?
) : Parcelable