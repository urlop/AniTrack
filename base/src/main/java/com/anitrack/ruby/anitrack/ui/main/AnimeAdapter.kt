package com.anitrack.ruby.anitrack.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.anitrack.ruby.anitrack.R
import com.anitrack.ruby.anitrack.data.source.remote.models.AnimeWS
import com.anitrack.ruby.anitrack.ui.detail.AnimeDetailFragment
import com.anitrack.ruby.anitrack.utils.ViewUtils
import com.squareup.picasso.Picasso

class AnimeAdapter(val items: ArrayList<AnimeWS>, val context: Context) : RecyclerView.Adapter<AnimeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_anime, parent, false))
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        //TODO Animate correctly while appearing

        val item: AnimeWS = items.get(position)

        val averageRating = item.attributes.averageRating ?: "0"
        val averageStar = averageRating.toFloat() * 5 / 100 //100 points to 5 stars

        // TODO get by titles order and check for blanks {if (english.isNullOrBlank()) english else null}
        // item.attributes.titles?.english ?: item.attributes.titles?.englishUniteStates ?: item.attributes.titles?.englishJapan ?: item.attributes.titles?.japanesseJapan ?: item.attributes.canonicalTitle
        holder.tv_name?.text = item.attributes.canonicalTitle
        holder.tv_rating?.text = ViewUtils.asRoundedDecimal(item.attributes.averageStar ?: 0f, 1)
        Picasso.get().load(item.attributes.posterImage?.small).into(holder.iv_background)

        holder.itemView.setOnClickListener {
            val bundle = bundleOf(AnimeDetailFragment.ARG_ANIME to item)

            Navigation.findNavController(holder.itemView).navigate(
                    R.id.action_mainFragment_to_animeDetailFragment,
                    bundle)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addList(data: ArrayList<AnimeWS>, reset: Boolean) {
        if (reset) clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
    }
}