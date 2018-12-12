package com.elimalki.mykotlinapptest

import java.io.Serializable

/**
 *Created by Eli Malki on 10/12/2018
 *
 */
 class Task(val id: Long, val name: String, val description: String, val sortOrder: Int) : Serializable {

    companion object {
        val SerialVersionUID = 20161120L
    }
}