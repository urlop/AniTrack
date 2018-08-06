package com.anitrack.ruby.anitrack.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anitrack.ruby.anitrack.R;
import com.anitrack.ruby.anitrack.network.models.DataAnime;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class JavaFragment extends Fragment {

    AnimeAdapter adapter;
    MainViewModel viewModel;

    public JavaFragment() {
    }

    public JavaFragment newInstance(){
        return new JavaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //MainViewModel model = ViewModelProviders.of(this).get(MainViewModel.class);

        /*model.getHeroes().observe(this, new Observer<List<RedditChildrenResponse>>() {
            @Override
            public void onChanged(@Nullable List<RedditChildrenResponse> list) {
                adapter = new AnimeAdapter((ArrayList<RedditChildrenResponse>) list, getContext());
                //recyclerView.setAdapter(adapter);
            }
        });*/

        /*model.getResult().observe(this, new Observer<List<DataAnime>>() {
            @Override
            public void onChanged(@Nullable List<DataAnime> list) {
                adapter = new AnimeAdapter((ArrayList<DataAnime>) list, getContext());
                //recyclerView.setAdapter(adapter);
            }
        });*/
    }

}
