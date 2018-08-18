package com.anitrack.ruby.anitrack.ui.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.anitrack.ruby.anitrack.R
import com.anitrack.ruby.anitrack.network.models.DataAnime
import com.anitrack.ruby.anitrack.utils.ViewUtils
import com.squareup.picasso.Picasso

class AnimeAdapter(val items: ArrayList<DataAnime>, val context: Context) : RecyclerView.Adapter<AnimeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        return AnimeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_anime, parent, false))
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        //TODO Animate correctly while appearing

        val averageRating = items.get(position).attributes.averageRating ?: "0"
        val averageStar = averageRating.toFloat() * 5 / 100 //100 points to 5 stars

        // TODO get by titles order and check for blanks {if (english.isNullOrBlank()) english else null}
        // items.get(position).attributes.titles?.english ?: items.get(position).attributes.titles?.englishUniteStates ?: items.get(position).attributes.titles?.englishJapan ?: items.get(position).attributes.titles?.japanesseJapan ?: items.get(position).attributes.canonicalTitle
        holder.tv_name?.text = items.get(position).attributes.canonicalTitle
        holder.tv_rating?.text = ViewUtils.asRoundedDecimal(averageStar, 1)
        Picasso.get().load(items.get(position).attributes.posterImage?.small).into(holder.iv_background)

        holder.itemView.setOnClickListener{
            val bundle = bundleOf("ARG_ANIME" to items.get(position))

            //TODO Fix back removes first element randomly
            Navigation.findNavController(holder.itemView).navigate(
                    R.id.action_mainFragment_to_animeDetailFragment,
                    bundle)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addList(data: ArrayList<DataAnime>, reset: Boolean) {
        if (reset) clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
    }
}