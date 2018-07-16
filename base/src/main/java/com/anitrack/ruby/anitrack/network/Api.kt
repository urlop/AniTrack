package com.anitrack.ruby.anitrack.network

import com.anitrack.ruby.anitrack.network.models.BaseAnime
import com.anitrack.ruby.anitrack.ui.main.RedditNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("trending/anime.json")
    fun getAnimesTrending()
            : Call<BaseAnime>;
}