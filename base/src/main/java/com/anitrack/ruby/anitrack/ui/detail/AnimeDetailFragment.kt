package com.anitrack.ruby.anitrack.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anitrack.ruby.anitrack.R
import com.anitrack.ruby.anitrack.TempToolbarTitleListener
import com.anitrack.ruby.anitrack.data.AnimeRepository
import com.anitrack.ruby.anitrack.data.GenreRepository
import com.anitrack.ruby.anitrack.network.RetrofitClient
import com.anitrack.ruby.anitrack.network.models.DataAnime
import com.anitrack.ruby.anitrack.network.models.genre.Genre
import com.anitrack.ruby.anitrack.utils.ViewUtils
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_anime_detail.*

class AnimeDetailFragment : Fragment() {

    companion object {
        val ARG_ANIME = "ARG_ANIME"
    }

    private lateinit var viewModel: GenreViewModel
    private lateinit var observerResult: Observer<List<Genre>>
    private lateinit var observerNetworkErros: Observer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
                .get(GenreViewModel::class.java)
        viewModel.repository = GenreRepository(RetrofitClient())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val anime: DataAnime = arguments!!.getParcelable<DataAnime>(ARG_ANIME)

        //TODO Change actionBar's title with anime.attributes.canonicalTitle
        (activity as TempToolbarTitleListener).updateTitle(anime.attributes.canonicalTitle
                ?: "Anime Detail")
        tv_name.text = if (anime.attributes.titles?.englishJapan != anime.attributes.canonicalTitle) anime.attributes.titles?.englishJapan else ""
        tv_summary.text = anime.attributes.synopsis
        tv_rating.text = ViewUtils.asRoundedDecimal(anime.attributes.averageStar ?: 0f, 1)

        //TODO Add genres as chips
        /*anime.relationships?.genres?.links?.related
        for (index in tags.indices) {
            val chip = Chip(cg_cathegories.context)
            chip.text = "Item ${tags[index]}"

            // necessary to get single selection working
            chip.isClickable = true
            chip.isCheckable = true
            cg_cathegories.addView(chip)
        }*/

        if (tv_name.text.isEmpty()) {
            tv_name.visibility = View.GONE
        }

        viewModel.search(anime.id ?: "0");

        observerResult = Observer { list ->
            for (item in list) {
                val chip = Chip(cg_genres.context)
                chip.text = item.attributes?.name

                // necessary to get single selection working
                chip.isClickable = true
                chip.isCheckable = false
                cg_genres.addView(chip)
            }
        }
        observerNetworkErros = Observer<String> {
            Toast.makeText(context, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        }

        viewModel.result.observe(this, observerResult)
        viewModel.networkErrors.observe(this, observerNetworkErros)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.result.removeObserver(observerResult)
        viewModel.networkErrors.removeObserver(observerNetworkErros)
    }

}
