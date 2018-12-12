package com.elimalki.mykotlinapptest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 *Created by Eli Malki on 09/12/2018
 *
 */
val DATABASE_VERSION: String = "1"
var instance: AppdDataBase? = null

open class AppdDataBase(val context: Context) : SQLiteOpenHelper(context, DATABASE_VERSION, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

//        val dd = TaskContract().TABLE_NAME
//        println(dd)
//        var sSql = ("CREATE TABLE " + TaskContract().TABLE_NAME + "("
//                + TaskContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
//                + TaskContract.Columns.TASKS_NAME + " TEXT NOT NULL, "
//                + TaskContract.Columns.TASKS_DESCRIPTION + " TEXT, "
//                + TaskContract.Columns.TASKS_SORTORDER + " INTEGER); ")
//        db?.execSQL(sSql)

        //  addTimingTable(db)

        // geneate data
//        sSql = "insert into tasks(name,description) values('stam','Do some stam things');"
//        db?.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Kosher','do some kosher');"
//        db?.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Learn','Learn Java');"
//        db?.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('TV','Watch TV');"
//        db?.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Ella','PLay with Ellalush');"
//        db?.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Maya','Talk to Mayush');"
//        db?.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Songs','Listen to songs');"
//        db?.execSQL(sSql)


    }

    fun getInstance(context: Context): AppdDataBase {
        if (instance == null) {
            instance = AppdDataBase(context)
        }
        return instance as AppdDataBase
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when (newVersion) {
        }

    }


}