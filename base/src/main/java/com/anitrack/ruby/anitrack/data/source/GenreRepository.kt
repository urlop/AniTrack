package com.anitrack.ruby.anitrack.data.source

import androidx.lifecycle.MutableLiveData
import com.anitrack.ruby.anitrack.data.source.remote.GenresResult
import com.anitrack.ruby.anitrack.data.source.remote.RetrofitClient
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.BaseGenre
import com.anitrack.ruby.anitrack.data.source.remote.models.genre.GenreWS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class that works with local and remote data sources.
 */
class GenreRepository(private val service: RetrofitClient) : GeneralRepository(service) {

    var genreList: MutableLiveData<List<GenreWS>> = MutableLiveData()

    fun search(id: String): GenresResult {
        requestgenreList(id)

        return GenresResult(genreList, networkErrors)
    }

    private fun requestgenreList(id: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true

        service.getAnimeGenre(id)
                .enqueue(object : Callback<BaseGenre> {
                    override fun onResponse(call: Call<BaseGenre>, response: Response<BaseGenre>) {
                        try {
                            genreList.postValue(response.body()!!.data)
                            isRequestInProgress = false
                        } catch (e: Exception) {
                            networkErrors.postValue("GenresWS not available") //404 {"error":"We couldn't find the record you were looking for."}
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