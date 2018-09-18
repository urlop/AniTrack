package com.anitrack.ruby.anitrack.utils

import android.content.Context
import android.util.TypedValue

class ViewUtils {
    companion object {
        fun asRoundedDecimal(number: Float, decimals: Number): String {
            return "%.${decimals}f".format(number)
        }
    }
}