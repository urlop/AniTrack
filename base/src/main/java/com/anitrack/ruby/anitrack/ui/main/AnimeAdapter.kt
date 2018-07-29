package com.anitrack.ruby.anitrack.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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
        val averageStar = items.get(position).attributes.averageRating.toFloat() * 5 / 100 //100 points to 5 stars

        holder.tv_name?.text = items.get(position).attributes.titles?.english
        holder.tv_rating?.text = ViewUtils.asRoundedDecimal(averageStar, 1)
        Picasso.get().load(items.get(position).attributes.posterImage?.small).into(holder.iv_background)
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