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

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // LiveData of network errors.
    private val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    var animeList: MutableLiveData<List<DataAnime>> = MutableLiveData()

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): AnimeSearchResult {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1

        requestAnimeList(query)

        return AnimeSearchResult(animeList, networkErrors)
    }

    fun requestMore(query: String) {
        requestAnimeList(query)
    }

    private fun requestAnimeList(query: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true

        service.getAnimesTrending()
                .enqueue(object : Callback<BaseAnime> {
                    override fun onResponse(call: Call<BaseAnime>, response: Response<BaseAnime>) {
                        lastRequestedPage++
                        isRequestInProgress = false
                        animeList?.postValue(response.body()!!.data)
                    }

                    override fun onFailure(call: Call<BaseAnime>, t: Throwable) {
                        networkErrors.postValue(t.message)
                        isRequestInProgress = false
                    }
                })
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }
}