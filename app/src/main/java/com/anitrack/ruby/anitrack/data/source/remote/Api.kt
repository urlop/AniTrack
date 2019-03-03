package com.anitrack.ruby.anitrack.data.source.remote

import com.anitrack.ruby.anitrack.data.source.remote.models.BaseAnime
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.BaseGenre
import com.anitrack.ruby.anitrack.data.source.remote.models.streaming.BaseStreaming
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("trending/anime.json")
    fun getAnimesTrending()
            : Call<BaseAnime>

    @GET("anime")
    fun getAnimes(@Query("page[limit]") pageLimit: Number,
                  @Query("page[offset]") pageOffset: Number,
                  @Query("sort") sort: String)
            : Call<BaseAnime>

    @GET("anime/{animeId}/genres")
    fun getAnimeGenres(@Path("animeId") animeId: String)
            : Call<BaseGenre>

    @GET("anime/{animeId}/streaming-links")
    fun getAnimeStreamingLinks(@Path("animeId") animeId: String)
            : Call<BaseStreaming>
}