package com.anitrack.ruby.anitrack.ui.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.anitrack.ruby.anitrack.data.GenreRepository
import com.anitrack.ruby.anitrack.data.StreamingRepository
import com.anitrack.ruby.anitrack.network.RetrofitClient
import com.anitrack.ruby.anitrack.network.models.DataAnime
import com.anitrack.ruby.anitrack.network.models.genre.Genre
import com.anitrack.ruby.anitrack.network.models.streaming.Streaming
import com.anitrack.ruby.anitrack.utils.ViewUtils
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_anime_detail.*
import android.net.Proxy.getHost
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.marginEnd
import com.anitrack.ruby.anitrack.utils.EnumStreaming
import java.net.URL
import com.anitrack.ruby.anitrack.MainActivity




class AnimeDetailFragment : Fragment() {

    companion object {
        val ARG_ANIME = "ARG_ANIME"
    }

    private lateinit var genreViewModel: GenreViewModel
    private lateinit var streamingViewModel: StreamingViewModel
    private lateinit var observerGenreResult: Observer<List<Genre>>
    private lateinit var observerStreamingResult: Observer<List<Streaming>>
    private lateinit var observerNetworkErrors: Observer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreViewModel = ViewModelProviders.of(this)
                .get(GenreViewModel::class.java)
        genreViewModel.repository = GenreRepository(RetrofitClient())
        streamingViewModel = ViewModelProviders.of(this)
                .get(StreamingViewModel::class.java)
        streamingViewModel.repository = StreamingRepository(RetrofitClient())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val anime: DataAnime = arguments!!.getParcelable<DataAnime>(ARG_ANIME)

        (activity as TempToolbarTitleListener).updateTitle(anime.attributes.canonicalTitle
                ?: "Anime Detail")
        tv_name.text = if (anime.attributes.titles?.englishJapan != anime.attributes.canonicalTitle) anime.attributes.titles?.englishJapan else ""
        tv_summary.text = anime.attributes.synopsis
        tv_rating.text = ViewUtils.asRoundedDecimal(anime.attributes.averageStar ?: 0f, 1)
        Picasso.get().load(anime.attributes.posterImage?.medium).into(iv_background)

        if (tv_name.text.isEmpty()) {
            tv_name.visibility = View.GONE
        }

        iv_background.setOnClickListener {
            watchYoutubeVideo(context!!, anime.attributes.youtubeVideoId!!)
        }

        genreViewModel.search(anime.id ?: "0");
        streamingViewModel.search(anime.id ?: "0");

        observerGenreResult = Observer { list ->
            //TODO Filter by genre
            for (item in list) {
                val chip = Chip(cg_genres.context)
                chip.text = item.attributes?.name

                // necessary to get single selection working
                chip.isClickable = true
                chip.isCheckable = false
                cg_genres.addView(chip)
            }
        }

        observerStreamingResult = Observer { list ->
            //TODO: Add click to link to webpage
            for (item in list) {
                val url = URL(item.attributes?.url)
                val host = url.getHost()

                val imageView = ImageView(context)
                val params = LinearLayout.LayoutParams(
                        getResources().getDimension(R.dimen.streaming_button_size).toInt(),
                        getResources().getDimension(R.dimen.streaming_button_size).toInt())
                params.marginEnd = getResources().getDimension(R.dimen.space_10).toInt();
                imageView.setImageResource(EnumStreaming.getByUrl(host).drawable)
                imageView.layoutParams = params
                (v_container_watch_on as ViewGroup).addView(imageView)
            }
        }

        observerNetworkErrors = Observer<String> {
            Toast.makeText(context, "\uD83D\uDE28 Wooops ${it}", Toast.LENGTH_LONG).show()
        }

        genreViewModel.result.observe(this, observerGenreResult)
        genreViewModel.networkErrors.observe(this, observerNetworkErrors)
        streamingViewModel.result.observe(this, observerStreamingResult)
        streamingViewModel.networkErrors.observe(this, observerNetworkErrors)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        genreViewModel.result.removeObserver(observerGenreResult)
        genreViewModel.networkErrors.removeObserver(observerNetworkErrors)
        streamingViewModel.result.removeObserver(observerStreamingResult)
        streamingViewModel.networkErrors.removeObserver(observerNetworkErrors)
    }

    fun watchYoutubeVideo(context: Context, id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=$id"))
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }

    }
}
