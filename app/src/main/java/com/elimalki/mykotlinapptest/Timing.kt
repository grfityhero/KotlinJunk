package com.elimalki.mykotlinapptest

import android.util.Log
import java.io.Serializable
import java.util.*

/**
 *Created by Eli Malki on 10/12/2018
 *
 */
internal class Timing(var task: Task?) : Serializable {

    var id: Long = 0
    var startTime: Long = 0
    var duration: Long = 0
        private set

    init {
        //init start time and duration to zero 0
        val currentTime = Date()
        startTime = currentTime.time / 1000
        duration = 0
    }

    fun setDuration() {
        //calculate duration:
        val currentTime = Date()
        duration = currentTime.time / 1000 - startTime

        Log.d(TAG, "setDuration: " + (task?.id ?: -1) + "  - StartTime: " + startTime + " | Duration: " + duration)
    }

    companion object {
        val SerialVersionUID = 20161120L
        private val TAG = "Timing"
    }
}
