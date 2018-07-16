package com.anitrack.ruby.anitrack.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anitrack.ruby.anitrack.R
import kotlinx.android.synthetic.main.main_fragment.*
import com.anitrack.ruby.anitrack.network.models.DataAnime


class MainFragment : Fragment() {

    lateinit var adapter: AnimeAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AnimeAdapter(arrayListOf(), context!!)
        rv_list.adapter = adapter
        rv_list.layoutManager = LinearLayoutManager(context!!)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.getAnimesTrending().observe(this, Observer { list ->
            adapter.addList(list as ArrayList<DataAnime>, true)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.clear()
    }
}
