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
import com.anitrack.ruby.anitrack.data.source.remote.RetrofitClient
import com.anitrack.ruby.anitrack.data.source.remote.models.DataAnime
import com.anitrack.ruby.anitrack.utils.ViewUtils
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_anime_detail.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.anitrack.ruby.anitrack.data.source.local.models.Anime
import com.anitrack.ruby.anitrack.ui.BaseFragment
import com.anitrack.ruby.anitrack.ui.ViewModelFactory
import com.anitrack.ruby.anitrack.utils.EnumStreaming
import java.net.URL
import com.anitrack.ruby.anitrack.ui.OnBackPressedListener
import com.hlab.fabrevealmenu.listeners.OnFABMenuSelectedListener


class AnimeDetailFragment : BaseFragment(), OnFABMenuSelectedListener, OnBackPressedListener {
    override fun onBackPressed(): Boolean {
        if (fab_save_menu != null) {
            //action not popBackStack
            if (fab_save_menu.isShowing()) {
                fab_save_menu.closeMenu();
                return true;
            }
        }
        return false;
    }

    override fun onMenuItemSelected(view: View?, id: Int) {
        if (id == R.id.menu_watched) {
            Toast.makeText(context, "menu_watched", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_watch_later) {
            Toast.makeText(context, "menu_watch_later", Toast.LENGTH_SHORT).show();
        }
    }

    companion object {
        val ARG_ANIME = "ARG_ANIME"
    }

    private lateinit var viewModel: AnimeDetailViewModel
    private lateinit var observerExtraDetailResult: Observer<Anime>
    private lateinit var observerNetworkErrors: Observer<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val anime: DataAnime = arguments!!.getParcelable<DataAnime>(ARG_ANIME)

        viewModel = ViewModelProviders.of(this, ViewModelFactory.newInstance(context!!, RetrofitClient(), listOf(anime))).get(AnimeDetailViewModel::class.java)

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

        //attach menu to fab
        fab_save_menu.bindAnchorView(fab_save);
        //set menu selection listener
        fab_save_menu.setOnFABMenuSelectedListener(this);

        viewModel.search(anime.id ?: "0")

        observerExtraDetailResult = Observer {
            if (it.genres == null || it.streamingLinks == null) return@Observer
            cg_genres.removeAllViews()
            for (item in it.genres!!) {
                val chip = Chip(cg_genres.context)
                chip.text = item.name
                chip.setTextColor(ContextCompat.getColor(context!!, R.color.white))
                chip.setChipBackgroundColorResource(R.color.colorAccent)

                // necessary to get single selection working
                chip.isClickable = true
                chip.isCheckable = false
                cg_genres.addView(chip)
            }

            (v_container_watch_on as ViewGroup).removeAllViews()
            for (item in it.streamingLinks!!) {
                val url = URL(item.url)
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

        viewModel.liveDataMerger!!.reObserve(viewLifecycleOwner, observerExtraDetailResult)
        viewModel.genreNetworkErrors!!.reObserve(viewLifecycleOwner, observerNetworkErrors)
        viewModel.streamingNetworkErrors!!.reObserve(viewLifecycleOwner, observerNetworkErrors)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.liveDataMerger!!.removeObserver(observerExtraDetailResult)
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
