package com.anitrack.ruby.anitrack.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anitrack.ruby.anitrack.R
import com.anitrack.ruby.anitrack.data.source.AnimeRepository
import com.anitrack.ruby.anitrack.data.source.remote.RetrofitClient
import kotlinx.android.synthetic.main.main_fragment.*
import com.anitrack.ruby.anitrack.data.source.remote.models.DataAnime
import com.anitrack.ruby.anitrack.ui.BaseFragment
import com.anitrack.ruby.anitrack.ui.ViewModelFactory


class MainFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var adapter: AnimeAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var observerResult: Observer<List<DataAnime>>
    private lateinit var observerNetworkErrors: Observer<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(context!!, RetrofitClient())).get(MainViewModel::class.java)
        setup()

        observerResult = Observer { list ->
            adapter.addList(list as ArrayList<DataAnime>, true)
            srl_refresh.isRefreshing = false
        }
        observerNetworkErrors = Observer<String> {
            Toast.makeText(context, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
            srl_refresh.isRefreshing = false
        }

        viewModel.result.reObserve(viewLifecycleOwner, observerResult)
        viewModel.networkErrors.reObserve(viewLifecycleOwner, observerNetworkErrors)
    }

    fun setup() {
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

        //Refresh setup
        srl_refresh.isEnabled = true
        srl_refresh.setOnRefreshListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter.clear()
        viewModel.result.removeObserver(observerResult)
        viewModel.networkErrors.removeObserver(observerNetworkErrors)
    }

    override fun onRefresh() {
        adapter.clear()
        viewModel.searchAnime(AnimeRepository.SORT_POPULARITY, false, true);
    }

}
