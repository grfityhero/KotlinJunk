package com.elimalki.mykotlinapptest


import android.content.res.Configuration
import android.database.Cursor
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

class MainActivity() : AppCompatActivity(),OnTaskClickListener , AddEDitActivityFragment.OnSavedClicked {
    override fun OnSavedClicked() {
        Log.d(TAG, "OnSavedClicked: ")
        //OnSavedClicked (interface) was clicked in AddEditActivity lone 183
        //so removing the edit fragment and hiding the  container frame:

        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentById(R.id.task_detalis_container)
        if (fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        Log.d(TAG, "OnSavedClicked: edit fragment removed. ")


        val addEditLayout:View = findViewById(R.id.task_detalis_container)
        val mainFragment:View  = findViewById(R.id.fragment)

        if (!mTwoPaneMode) {
            //we've just remove the editing fragment ,so hide the frame.
            addEditLayout.setVisibility(View.GONE)
            mainFragment.setVisibility(View.VISIBLE)
            Log.d(TAG, "OnSavedClicked: hiding task_detalis_container  GONE")
        }
    }


    var mTwoPaneMode: Boolean = false
    private val TAG = "MainActivity"

    constructor(parcel: Parcel) : this() {
        mTwoPaneMode = parcel.readByte() != 0.toByte()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         myActions()

        mTwoPaneMode = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val fragmentManager = supportFragmentManager


        val mainFragment: View  = findViewById(R.id.fragment)
        val addEditLayout : View = findViewById(R.id.task_detalis_container)

        when (mTwoPaneMode) {

            true -> {
                mainFragment.visibility = View.VISIBLE
                addEditLayout.visibility = View.VISIBLE
            }
            false->{

                mainFragment.visibility = View.VISIBLE
                addEditLayout.visibility = View.GONE
            }

        }

    }

    override fun onEditClickListener(task: Task) {
        Log.d(TAG, "onEditClickListener: ")
        taskEditRequest(task)
    }

    private fun taskEditRequest(task: Task) {

        Log.d(TAG, "taskEditRequest: starts")
        val fragment = AddEDitActivityFragment()
        val arguments = Bundle()
        arguments.putSerializable(Task::class.java.simpleName, task)
        fragment.setArguments(arguments)
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_detalis_container, fragment)
            .commit()
        val mainFragment: View  = findViewById(R.id.fragment)
        val addEditLayout : View = findViewById(R.id.task_detalis_container)


        if (mTwoPaneMode) {
            mainFragment.setVisibility(View.VISIBLE)
            addEditLayout.setVisibility(View.VISIBLE)
        } else {
            mainFragment.setVisibility(View.GONE)
            addEditLayout.setVisibility(View.VISIBLE)
        }
        Log.d(TAG, "Existing taskEditRequest: ")

    }


    override fun onDeleteClick(task: Task) {
        Log.d(TAG, "onDeleteClick: ")

    }

    override fun onLongClickListener(task: Task) {
        Log.d(TAG, "onLongClickListener: ")

    }


    private fun myActions() {
        val mAppDatabase = AppdDataBase(this).getInstance(this)
        val db = mAppDatabase.getWritableDatabase()
        var sSql: String

//         sSql = ("CREATE TABLE " + TaskContract().TABLE_NAME + "("
//                + TaskContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
//                + TaskContract.Columns.TASKS_NAME + " TEXT NOT NULL, "
//                + TaskContract.Columns.TASKS_DESCRIPTION + " TEXT, "
//                + TaskContract.Columns.TASKS_SORTORDER + " INTEGER); ")
//        db.execSQL(sSql)


//        sSql = "insert into tasks(name,description) values('stam','Do some stam things');"
//        db.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Kosher','do some kosher');"
//        db.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Learn','Learn Java');"
//        db.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('TV','Watch TV');"
//        db.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Ella','PLay with Ellalush');"
//        db.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Maya','Talk to Mayush');"
//        db.execSQL(sSql)
//        sSql = "insert into tasks(name,description) values('Songs','Listen to songs');"
//        db.execSQL(sSql)

//        sSql = ("CREATE TABLE " + TimingsContract().TABLE_NAME + "("
//                + TimingsContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
//                + TimingsContract.Columns.TIMINGS_TASK_ID + " INTEGER NOT NULL, "
//                + TimingsContract.Columns.TIMING_STRAT_TIME + " INTEGER, "
//                + TimingsContract.Columns.TIMINGS_DURATION + " INTEGER); ")
//
//        db.execSQL(sSql)


        /*   CREATE VIEW vwTaskDurations AS
        SELECT Timings._id,
                Tasks.Name,
                Tasks.Description,
                Timings.StartTime,
                DATE(Timings.StartTime, 'unixepoch') AS StartDate,
        SUM(Timings.Duration) AS Duration
        FROM Tasks INNER JOIN Timings
        ON Tasks._id = Timings.TaskId
        GROUP BY Tasks._id, StartDate;
        */

//        val sqlStr = ("CREATE VIEW vwTaskDurations"
//                + " AS SELECT " + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns._ID + ", "
//                + "Tasks.Name" + ", "
//                + TaskContract.TABLE_NAME + "." + TaskContract.Columns.TASKS_DESCRIPTION + ", "
//                + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMING_STRAT_TIME + ","
//                + " DATE(" + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMING_STRAT_TIME + ", 'unixepoch')"
//                + " AS " + DurationContract.Columns.DURATIONS_START_DATE + ","
//                + " SUM(" + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMINGS_DURATION + ")"
//                + " AS " + DurationContract.Columns.DURATIONS_DURATION
//                + " FROM " + TaskContract.TABLE_NAME + " JOIN " + TimingsContract.TABLE_NAME
//                + " ON " + TaskContract.TABLE_NAME + "." + TaskContract.Columns._ID + " = "
//                + TimingsContract.TABLE_NAME + "." + TimingsContract.Columns.TIMINGS_TASK_ID
//                + " GROUP BY " + DurationContract.Columns.DURATIONS_START_DATE + ", " + DurationContract.Columns.DURATIONS_NAME)

        // String  sqlStr =    "DROP TRIGGER  Remove_Task";
        //val sqlStr: String    = "CREATE TRIGGER Remove_Task AFTER DELETE ON Tasks FOR EACH ROW BEGIN DELETE FROM Timings WHERE TaskId = OLD._id" +"; END";


        // String sqlStr = "DROP VIEW  vwTaskDurations";
        //  sSql = "DELETE FROM TaskTimer";

        // val currentCalenderDate = mCalendar.getTime()

        // db.execSQL(sqlStr)
        //val mCursor = db.rawQuery("SELECT * FROM vwTaskDurations", null)
        lateinit var cursor: Cursor
        cursor = db.rawQuery("SELECT * FROM Tasks", null);
        //mCursor = db.rawQuery("SELECT * FROM Tasks", null);
        //  mCursor  = db.execSQL("SELECT * FROM Tasks");

//        if (mCursor != null) {
//            while (mCursor!!.moveToNext()) {
//                for (i in 0 until mCursor!!.getColumnCount()) {
//                    Log.d(TAG, "||" + mCursor!!.getColumnName(i) + ": " + mCursor!!.getString(i))
//
//                }
//                Log.d(TAG, "===================================")
//            }
//            mCursor!!.close()
//            Log.d(TAG, "===================================" + mCursor!!.getCount())
//        }

    }




    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }
}

