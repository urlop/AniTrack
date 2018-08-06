package com.anitrack.ruby.anitrack.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anitrack.ruby.anitrack.data.KitsuRepository
import com.anitrack.ruby.anitrack.model.AnimeSearchResult
import com.anitrack.ruby.anitrack.network.models.DataAnime


class MainViewModel() : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    //this is the data that we will fetch asynchronously
    //private val animeList: MutableLiveData<List<DataAnime>>? = null
   // var error: MutableLiveData<Boolean>? = null;
    lateinit var repository: KitsuRepository

    private val queryLiveData = MutableLiveData<String>()
    private val animeResult: LiveData<AnimeSearchResult> = Transformations.map(queryLiveData, {
        repository.search(it)
    })

    val result: LiveData<List<DataAnime>> = Transformations.switchMap(animeResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(animeResult,
            { it -> it.networkErrors })

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value


    /*fun getAnimesTrending(): LiveData<List<DataAnime>> {
        //if the list is null
        if (animeList == null) {
            animeList = MutableLiveData()
            //we will load it asynchronously from server in this method
            loadAnimesTrending()
        }

        //finally we will return the list
        return animeList as MutableLiveData<List<DataAnime>>
    }

    private fun loadAnimesTrending() {
        RetrofitClient().getAnimesTrending()
                .enqueue(object : Callback<BaseAnime> {
                    override fun onResponse(call: Call<BaseAnime>, response: Response<BaseAnime>) {
                        animeList?.value = response.body()!!.data;
                    }

                    override fun onFailure(call: Call<BaseAnime>, t: Throwable) {
                        Log.d("MainViewModel", t.message);
                        error?.value = true;
                    }
                })
    }*/

}
