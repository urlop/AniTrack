package com.anitrack.ruby.anitrack.network

import com.anitrack.ruby.anitrack.ui.main.RedditNewsResponse
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class RetrofitClient {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(Api::class.java)
    }

    fun getNews(after: String, limit: String): Call<RedditNewsResponse> {
        return api.getTop(after, limit)
    }
}