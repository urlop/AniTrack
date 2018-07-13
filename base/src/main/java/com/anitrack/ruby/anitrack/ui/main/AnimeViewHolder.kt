package com.anitrack.ruby.anitrack.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_anime.view.*


class AnimeViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvName = view.tv_name
}