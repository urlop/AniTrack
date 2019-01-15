package com.anitrack.ruby.anitrack.data.source

import androidx.lifecycle.MutableLiveData
import com.anitrack.ruby.anitrack.data.source.remote.AnimeSearchResult
import com.anitrack.ruby.anitrack.data.source.remote.RetrofitClient
import com.anitrack.ruby.anitrack.data.source.remote.models.BaseAnime
import com.anitrack.ruby.anitrack.data.source.remote.models.AnimeWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class that works with local and remote data sources.
 */
class AnimeRepository(private val service: RetrofitClient) : GeneralRepository(service) {

    companion object {
        const val SORT_POPULARITY = "ratingRank"

        private const val NETWORK_PAGE_SIZE = 10
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastPageOffset = 0

    var animeList: MutableLiveData<List<AnimeWS>> = MutableLiveData()

    fun search(sort: String): AnimeSearchResult {
        lastPageOffset = 0

        requestAnimeList(NETWORK_PAGE_SIZE, lastPageOffset, sort)

        return AnimeSearchResult(animeList, networkErrors)
    }

    fun requestMore(sort: String) {
        requestAnimeList(NETWORK_PAGE_SIZE, lastPageOffset, sort)
    }

    private fun requestAnimeList(pageLimit: Number, pageOffset: Number, sort: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true

        service.getAnimes(pageLimit, pageOffset, sort)
                .enqueue(object : Callback<BaseAnime> {
                    override fun onResponse(call: Call<BaseAnime>, response: Response<BaseAnime>) {
                        try {
                            lastPageOffset += NETWORK_PAGE_SIZE
                            if (animeList.value != null && animeList.value!!.isNotEmpty()) {
                                animeList.postValue((animeList.value!!.toTypedArray() + response.body()!!.data!!.toTypedArray()).toList())
                            } else {
                                animeList.postValue(response.body()!!.data)
                            }
                            isRequestInProgress = false
                        } catch (e : Exception) {
                            //TODO Handle 404 {"error":"We couldn't find the record you were looking for."}
                            isRequestInProgress = false
                        }
                    }

                    override fun onFailure(call: Call<BaseAnime>, t: Throwable) {
                        networkErrors.postValue(t.message)
                        isRequestInProgress = false
                    }
                })
    }

}