package com.anitrack.ruby.anitrack.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anitrack.ruby.anitrack.R
import com.anitrack.ruby.anitrack.data.KitsuRepository
import com.anitrack.ruby.anitrack.network.RetrofitClient
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
        Log.d("Log", "MainFragment onActivityCreated()");
        //TODO On back tries to crete a new fragment after entering detail and pressing back

        //List setup
        adapter = AnimeAdapter(arrayListOf(), context!!)
        rv_list.adapter = adapter
        rv_list.layoutManager = GridLayoutManager(context!!, 2)

        val layoutManager = rv_list.layoutManager as GridLayoutManager
        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })

        //ViewModle setup
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.repository = KitsuRepository(RetrofitClient())
        viewModel.searchAnime(KitsuRepository.SORT_POPULARITY, false);

        viewModel.result.observe(this, Observer { list ->
            adapter.addList(list as ArrayList<DataAnime>, true)
        })
        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(context, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.clear()
    }

}
