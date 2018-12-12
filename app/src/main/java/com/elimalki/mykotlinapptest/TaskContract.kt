package com.elimalki.mykotlinapptest

import android.content.ContentUris
import android.net.Uri

/**
 *Created by Eli Malki on 09/12/2018
 *
 */
 class TaskContract( val TABLE_NAME : String = "Tasks") {

fun getTaskId (uri :Uri) : Long{
    return ContentUris.parseId(uri)
}

    fun buildTaskUri (taskId :Long) : Uri{
        return ContentUris.withAppendedId( AppProvider.CONTENT_URI,taskId)
    }




  object Columns {
      val TASKS_NAME: String = "name"

      val TASKS_DESCRIPTION: String = "description"

      val TASKS_SORTORDER: String = "sortorder"

      val _ID: String = "_id"  //BaseColumns._ID
  }
}