package com.anitrack.ruby.anitrack.utils

class ViewUtils {
    companion object {
        fun asRoundedDecimal(number: Float, decimals: Number): String {
            return "%.${decimals}f".format(number)
        }
    }
}