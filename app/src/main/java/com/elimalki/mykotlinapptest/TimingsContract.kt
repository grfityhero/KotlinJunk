package com.elimalki.mykotlinapptest

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

/**
 *Created by Eli Malki on 10/12/2018
 *
 */
 class TimingsContract(public val TABLE_NAME : String = "Timings") {

    fun buildTimingUri(TimingId: Long): Uri {
        return ContentUris.withAppendedId( AppProvider.CONTENT_URI, TimingId)
    }

    fun getTimingId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }


    object Columns {
        val _ID = BaseColumns._ID
        val TIMINGS_TASK_ID = "TaskId"
        val TIMING_STRAT_TIME = "StartTime"
        val TIMINGS_DURATION = "Duration"
    }

}