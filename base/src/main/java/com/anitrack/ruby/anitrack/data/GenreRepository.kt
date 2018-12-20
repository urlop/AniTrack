package com.anitrack.ruby.anitrack.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anitrack.ruby.anitrack.model.AnimeSearchResult
import com.anitrack.ruby.anitrack.model.GenresResult
import com.anitrack.ruby.anitrack.network.RetrofitClient
import com.anitrack.ruby.anitrack.network.models.BaseAnime
import com.anitrack.ruby.anitrack.network.models.DataAnime
import com.anitrack.ruby.anitrack.network.models.genre.BaseGenre
import com.anitrack.ruby.anitrack.network.models.genre.Genre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class that works with local and remote data sources.
 */
class GenreRepository(private val service: RetrofitClient) : GeneralRepository(service) {

    var genreList: MutableLiveData<List<Genre>> = MutableLiveData()

    fun search(id: String): GenresResult {
        requestgenreList(id)

        return GenresResult(genreList, networkErrors)
    }

    private fun requestgenreList(id: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true

        service.getGenre(id)
                .enqueue(object : Callback<BaseGenre> {
                    override fun onResponse(call: Call<BaseGenre>, response: Response<BaseGenre>) {
                        try {
                            if (genreList.value != null && genreList.value!!.isNotEmpty()) {
                                genreList.postValue((genreList.value!!.toTypedArray() + response.body()!!.data!!.toTypedArray()).toList())
                            } else {
                                genreList.postValue(response.body()!!.data)
                            }
                            isRequestInProgress = false
                        } catch (e: Exception) {
                            networkErrors.postValue("Genres not available") //404 {"error":"We couldn't find the record you were looking for."}
                            isRequestInProgress = false
                        }
                    }

                    override fun onFailure(call: Call<BaseGenre>, t: Throwable) {
                        networkErrors.postValue(t.message)
                        isRequestInProgress = false
                    }
                })
    }

}