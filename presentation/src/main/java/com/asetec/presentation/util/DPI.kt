package com.asetec.presentation.util

import android.content.Context

fun getDPI(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return displayMetrics.xdpi.toInt()
}