package com.anitrack.ruby.anitrack.data.source.remote

import com.anitrack.ruby.anitrack.data.source.remote.models.BaseAnime
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.BaseGenre
import com.anitrack.ruby.anitrack.data.source.remote.models.streaming.BaseStreaming
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class RetrofitClient {
    private val api: Api

    init {
        val httpClient = OkHttpClient().newBuilder()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder().
                    header("Accept", "application/vnd.api+json").
                    header("Content-Type", "application/vnd.api+json").
                    build();
            chain.proceed(request)
        }
        httpClient.networkInterceptors().add(interceptor)
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder ()
                .baseUrl("https://kitsu.io/api/edge/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

        api = retrofit.create(Api::class.java)
    }

    fun getAnimesTrending(): Call<BaseAnime> {
        return api.getAnimesTrending()
    }

    fun getAnimes(pageLimit: Number, pageOffset: Number, sort: String): Call<BaseAnime> {
        return api.getAnimes(pageLimit, pageOffset, sort)
    }

    fun getAnimeGenre(animeId: String): Call<BaseGenre> {
        return api.getAnimeGenres(animeId)
    }

    fun getAnimeStreaming(animeId: String): Call<BaseStreaming> {
        return api.getAnimeStreamingLinks(animeId)
    }
}