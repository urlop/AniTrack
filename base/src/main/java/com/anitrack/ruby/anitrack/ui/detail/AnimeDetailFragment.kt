package com.anitrack.ruby.anitrack.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anitrack.ruby.anitrack.R
import com.anitrack.ruby.anitrack.network.models.DataAnime
import kotlinx.android.synthetic.main.fragment_anime_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AnimeDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AnimeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AnimeDetailFragment : Fragment() {

    companion object {
        val ARG_ANIME = "ARG_ANIME"
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
        tv_name.text = anime.attributes.canonicalTitle
        tv_summary.text = anime.attributes.synopsis
    }



}
