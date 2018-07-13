package com.anitrack.ruby.anitrack.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anitrack.ruby.anitrack.R
import kotlinx.android.synthetic.main.main_fragment.*
import com.anitrack.ruby.anitrack.MainActivity



class MainFragment : Fragment() {

    val list: ArrayList<String> = ArrayList()
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
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.getHeroes().observe(this, Observer { list ->
            adapter = AnimeAdapter((list as java.util.ArrayList<RedditChildrenResponse>?)!!, context!!)
            rv_list.adapter = adapter
            rv_list.layoutManager = LinearLayoutManager(context!!)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //list.add("Full Metal Alchemist")
        //list.add("Full Metal Panic")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.clear()
    }
}
