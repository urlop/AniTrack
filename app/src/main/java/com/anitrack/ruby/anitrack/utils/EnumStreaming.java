package com.anitrack.ruby.anitrack.utils;

import com.anitrack.ruby.anitrack.R;

import androidx.annotation.DrawableRes;

public enum EnumStreaming {

    AMAZON("www.amazon.com", R.drawable.ic_amazon),
    CRUNCHYROLL("www.crunchyroll.com", R.drawable.ic_crunchyroll),
    DAISUKE("motto.daisuki.net", R.drawable.ic_daisuke),
    FUNIANIMATION("www.funimation.com", R.drawable.ic_funianimation),
    HIDIVE("www.hidive.com", R.drawable.ic_hidive),
    HULU("www.hulu.com", R.drawable.ic_hulu),
    NETFLIX("www.netflix.com", R.drawable.ic_netflix),
    TUBITV("tubitv.com", R.drawable.ic_tubitv), //
    VIEWSTER("www.viewster.com", R.drawable.ic_viewster),
    YOUTUBE("www.youtube.com", R.drawable.ic_youtube),
    DEFAULT("", R.drawable.ic_amazon);

    private final String url;
    @DrawableRes
    private final int drawable;

    EnumStreaming(String url, @DrawableRes int drawable) {
        this.url = url;
        this.drawable = drawable;
    }

    public static EnumStreaming getByUrl(String other) {
        for (EnumStreaming value : values()) {
            if (value.url.equals(other)) {
                return value;
            }
        }
        return DEFAULT;
    }

    @DrawableRes
    public int getDrawable() {
        return this.drawable;
    }

}
