package com.anitrack.ruby.anitrack.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_anime.view.*


class AnimeViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tv_name = view.tv_name
    val iv_background = view.iv_background
    val tv_rating = view.tv_rating
}