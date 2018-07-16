package com.anitrack.ruby.anitrack.ui.main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import android.util.Log
import com.anitrack.ruby.anitrack.network.RetrofitClient
import com.anitrack.ruby.anitrack.network.models.BaseAnime
import com.anitrack.ruby.anitrack.network.models.DataAnime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {
    //this is the data that we will fetch asynchronously
    private var animeList: MutableLiveData<List<DataAnime>>? = null

    fun getAnimesTrending(): LiveData<List<DataAnime>> {
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
                    }
                })
    }

}
