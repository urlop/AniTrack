package com.anitrack.ruby.anitrack.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anitrack.ruby.anitrack.model.AnimeSearchResult
import com.anitrack.ruby.anitrack.network.RetrofitClient
import com.anitrack.ruby.anitrack.network.models.BaseAnime
import com.anitrack.ruby.anitrack.network.models.DataAnime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class that works with local and remote data sources.
 */
class KitsuRepository(private val service: RetrofitClient) {

    companion object {
        const val SORT_POPULARITY = "ratingRank"

        private const val NETWORK_PAGE_SIZE = 10
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastPageOffset = 1

    // LiveData of network errors.
    private val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    var animeList: MutableLiveData<List<DataAnime>> = MutableLiveData()

    /**
     * Search repositories whose names match the sort.
     */
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
                        lastPageOffset += NETWORK_PAGE_SIZE
                        if (animeList.value != null && animeList.value!!.isNotEmpty()) {
                            animeList.postValue((animeList.value!!.toTypedArray() + response.body()!!.data!!.toTypedArray()).toList())
                        } else {
                            animeList.postValue(response.body()!!.data)
                        }
                        isRequestInProgress = false
                    }

                    override fun onFailure(call: Call<BaseAnime>, t: Throwable) {
                        networkErrors.postValue(t.message)
                        isRequestInProgress = false
                    }
                })
    }

}