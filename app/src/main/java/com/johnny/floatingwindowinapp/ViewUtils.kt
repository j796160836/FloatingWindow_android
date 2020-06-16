package com.johnny.floatingwindowinapp

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToInt

object ViewUtils {
    fun getPixels(context: Context, inDP: Int): Int {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        return (inDP * displayMetrics.density).roundToInt()
    }
}