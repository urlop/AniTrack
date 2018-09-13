package com.anitrack.ruby.anitrack.network

import com.anitrack.ruby.anitrack.network.models.BaseAnime
import com.anitrack.ruby.anitrack.network.models.genre.BaseGenre
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

    @GET("anime/{id}/genres")
    fun getGenres(@Path("id") id: String)
            : Call<BaseGenre>
}