package com.anitrack.ruby.anitrack.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
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
        rv_list.layoutManager = GridLayoutManager(context!!, 2)

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
