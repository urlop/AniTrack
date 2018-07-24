package com.anitrack.ruby.anitrack.ui.main;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewJavaModel extends ViewModel {
    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<Hero>> heroList=null;
    private MutableLiveData<List<RedditChildrenResponse>> reddit=null;

    //we will call this method to get the data
    public LiveData<List<RedditChildrenResponse>> getHeroes(){
        //if the list is null
        if (reddit == null) {
            //heroList = MutableLiveData()
            reddit = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            //loadHeroes();
        }

        //finally we will return the list
        return reddit;
    }


    //This method is using Retrofit to get the JSON data from URL
    /*private void loadHeroes() {
        new RetrofitClient().getNews("test", "10")
                .enqueue(new Callback<RedditNewsResponse>() {
                    @Override
                    public void onResponse(Call<RedditNewsResponse> call, Response<RedditNewsResponse> response) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        //heroList!!.setValue(response.body())
                        reddit.setValue(response.body().getData().getChildren());
                    }

                    @Override
                    public void onFailure(Call<RedditNewsResponse> call, Throwable t) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                });
    }*/

}
