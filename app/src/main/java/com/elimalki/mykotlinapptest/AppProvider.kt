package com.elimalki.mykotlinapptest

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import android.util.Log

/**
 *Created by Eli Malki on 09/12/2018
 *
 */


class AppProvider : ContentProvider() {
    companion object {
        const val TASKS: Int = 100
        const val TASKS_ID: Int = 101
        const val TIMINGS: Int = 200
        const val TIMINGS_ID: Int = 201
//        const val DURATIONS: Int = 5
//        const val DURATIONS_ID: Int = 6
        const val CONTENT_AUTHORITY: String = "com.elimalki.mykotlinapptest.provider"
        val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://" + CONTENT_AUTHORITY)
        val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TaskContract().TABLE_NAME)
    }

    private val TAG = "AppProvider"
    lateinit var mAppDatabase: AppdDataBase
    val mUriMatcher: UriMatcher = buildUriMatcher()


    fun buildUriMatcher(): UriMatcher {
        Log.d(TAG, "buildUriMatcher: starts")
        //val match = UriMatcher(UriMatcher.NO_MATCH)
        val match: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        match.addURI(CONTENT_AUTHORITY, TaskContract().TABLE_NAME, TASKS)
        match.addURI(CONTENT_AUTHORITY, TaskContract().TABLE_NAME + "/#", TASKS_ID)
        match.addURI(CONTENT_AUTHORITY, TimingsContract().TABLE_NAME, TIMINGS)
        match.addURI(CONTENT_AUTHORITY, TimingsContract().TABLE_NAME + "/#", TIMINGS_ID)
//        match.addURI(CONTENT_AUTHORITY, TaskContract().TABLE_NAME, DURATIONS)
//        match.addURI(CONTENT_AUTHORITY, TaskContract().TABLE_NAME + "/#", DURATIONS_ID)
        Log.d(TAG, "buildUriMatcher: done")
        return match
    }

    override fun query(
        uri: Uri?,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {

        Log.d(TAG, "fun query")
       //test
//        val uriMatcher2 =UriMatcherTest
//        val reurnedMActher =   uriMatcher2.buildUriMatcher()
//        val matcher2: Int =  reurnedMActher.match(uri)


        val matcher: Int = mUriMatcher.match(uri)

        val queryBuilder = SQLiteQueryBuilder()
        val tskId: Long


        when (matcher) {
            TASKS -> queryBuilder.tables = TaskContract().TABLE_NAME
            TASKS_ID -> {
                queryBuilder.tables = TaskContract().TABLE_NAME
                tskId = TaskContract().getTaskId(uri!!)
                queryBuilder.appendWhere(TaskContract.Columns._ID + " = " + tskId)
            }

            TIMINGS -> queryBuilder.tables = TimingsContract().TABLE_NAME
            TIMINGS_ID -> {
                tskId = TaskContract().getTaskId(uri!!)
                queryBuilder.appendWhere(TimingsContract.Columns._ID + " = " + tskId)
            }

            else -> throw UnsupportedOperationException("Unknown uri: $uri")

//            DURATIONS ->queryBuilder.tables = TaskContract().TABLE_NAME
//            DURATIONS_ID ->{
//                tskId = TaskContract().getTaskId(uri as Uri)
//                queryBuilder.appendWhere(DurationsContract.Columns._ID + " = " + tskId)
//            }

        }


        val mDataBase = AppdDataBase(context).getInstance(this.context)
        val db: SQLiteDatabase = mDataBase.readableDatabase
        val cursor: Cursor =
            db.query(TaskContract().TABLE_NAME, projection, selection, selectionArgs, null, null, null, null)
        Log.d(TAG, "query Uri: " + uri)

        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }


    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        val sAppDataBase = AppdDataBase(context).getInstance(context)
        val macher: Int? = mUriMatcher.match(uri)
        val recId: Long
        val returnedUri: Uri
        val db: SQLiteDatabase = sAppDataBase.writableDatabase

        when (macher) {
            TASKS -> {
                recId = db.insert(TaskContract().TABLE_NAME, null, ContentValues())

                if (recId >= 0) {
                    returnedUri = TaskContract().buildTaskUri(recId)
                }
            }
            TIMINGS -> {
                recId = db.insert(TimingsContract().TABLE_NAME, null, ContentValues())

                if (recId >= 0) {
                    returnedUri = TaskContract().buildTaskUri(recId)
                }
            }
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }

        if (recId >= 0) {
            //something was inseted
            Log.d(TAG, "insert: notifyChange")


            context.contentResolver.notifyChange(uri, null)
        }

        return TaskContract().buildTaskUri(recId)

    }


    override fun onCreate(): Boolean {
        mAppDatabase = AppdDataBase(context).getInstance(getContext())


        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val uriType = mUriMatcher.match(uri)
        val db: SQLiteDatabase = mAppDatabase.writableDatabase
        val rowsUpdated: Int

        when (uriType) {
            TASKS -> rowsUpdated = db.update(
                TaskContract().TABLE_NAME,
                values,
                selection,
                selectionArgs
            )

            TASKS_ID -> {
                val id = TaskContract().getTaskId(uri as Uri)
                if (TextUtils.isEmpty(selection)) {

                    rowsUpdated = db.update(
                        TaskContract().TABLE_NAME,
                        values,
                        TaskContract.Columns._ID + "=" + id, null
                    )

                } else {
                    rowsUpdated = db.update(
                        TimingsContract().TABLE_NAME,
                        values,
                        TaskContract.Columns._ID + "=" + id
                                + " and "
                                + selection,
                        selectionArgs
                    )
                }
            }
            else -> throw IllegalArgumentException("Unknown URI: " + uri)
        }
        if (rowsUpdated > 0) {
            //somthing updated
            Log.d(TAG, "update: notifyChange")
            context!!.contentResolver.notifyChange(uri, null)
        }
      //  context.contentResolver.notifyChange(uri, null)
        return rowsUpdated
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri?): String {
//        val match = mUriMatcher.match(uri)
//        when (match) {
//            TASKS -> return TaskContract.CONTENT_TYPE
//            TASKS_ID -> return TaskContract.CONTENT_ITEM_TYPE
//            TIMINGS -> return TimingsContract.CONTENT_TYPE
//            TIMINGS_ID -> return TimingsContract.CONTENT_ITEM_TYPE
//            TASKS_DURATIONS -> return TimingsContract.CONTENT_TYPE
//            TASKS_DURATIONS_ID -> return TimingsContract.CONTENT_ITEM_TYPE
//        }
        return ""
    }
}