package com.anitrack.ruby.anitrack.ui.main

import android.arch.lifecycle.ViewModel
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import com.anitrack.ruby.anitrack.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {
    //this is the data that we will fetch asynchronously
    //private var heroList: MutableLiveData<List<Hero>>? = null
    private var reddit: MutableLiveData<List<RedditChildrenResponse>>? = null

    //we will call this method to get the data
    fun getHeroes(): LiveData<List<RedditChildrenResponse>> {
        //if the list is null
        if (reddit == null) {
            //heroList = MutableLiveData()
            reddit = MutableLiveData()
            //we will load it asynchronously from server in this method
            loadHeroes()
        }

        //finally we will return the list
        return reddit as MutableLiveData<List<RedditChildrenResponse>>
    }


    //This method is using Retrofit to get the JSON data from URL
    private fun loadHeroes() {
        RetrofitClient().getNews("test", "10")
                .enqueue(object : Callback<RedditNewsResponse> {
                    override fun onResponse(call: Call<RedditNewsResponse>, response: Response<RedditNewsResponse>) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        //heroList!!.setValue(response.body())
                        reddit?.value = response.body()!!.data.children;
                    }

                    override fun onFailure(call: Call<RedditNewsResponse>, t: Throwable) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }

}
