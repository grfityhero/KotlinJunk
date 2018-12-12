package com.elimalki.mykotlinapptest

import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by Eli Malki on 10/12/2018
 *
 */
internal class TestTiming(var taskID: Long, startTime: Long, var duration: Long) {
    var startTime: Long = 0

    init {
        this.startTime = startTime / 1000
        val myDate = getDate(this.startTime)
    }

    companion object {

        fun getDate(milliSeconds: Long): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            formatter.timeZone = TimeZone.getTimeZone("Israe;/Telaviv")
            return formatter.format(Date(milliSeconds))
        }
    }
}