package com.elimalki.mykotlinapptest

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.security.InvalidParameterException




class MainActivityFragment : Fragment(), OnTaskClickListener, LoaderManager.LoaderCallbacks<Cursor> {
 lateinit var  mactivity: Context
    val LOADER_ID: Int = 0
    var mListener :OnTaskClickListener? = null

    lateinit var mViewAdapter: MyRecyclerViewAdapter
    private val TAG = "MainActivityFragment"

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        Log.d(TAG, "onCreateLoader: ")
        val projection = arrayOf<String>(
            TaskContract.Columns._ID,
            TaskContract.Columns.TASKS_NAME,
            TaskContract.Columns.TASKS_DESCRIPTION,
            TaskContract.Columns.TASKS_SORTORDER
        )
        val sortOrder: String = TaskContract.Columns.TASKS_NAME

        context.let {
            when (id) {
                LOADER_ID -> {
//               return CursorLoader(activity!!,  CONTENT_URI, projection, null, null, sortOrder)
                    return CursorLoader(context!!, AppProvider.CONTENT_URI, projection, null, null, sortOrder)
                }

                else ->
                    throw InvalidParameterException("invalid loadr id")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: ")
        super.onActivityCreated(savedInstanceState)

        activity.let{ mactivity = activity!!}

        Log.d(TAG, "onActivityCreated: activity =  ${activity}")

        if (mactivity !is OnTaskClickListener) {
            if (mactivity != null) {
                throw ClassCastException("ERROR:" + mactivity.javaClass.simpleName + " must implement MyRecyclerViewAdapter.onTaskClickListener")
            }
        }


        loaderManager.initLoader(LOADER_ID, null, this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ")
        val view = inflater.inflate(R.layout.fragment_main_activity, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_tasks_list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        mViewAdapter = MyRecyclerViewAdapter(null, this)

        recyclerView.setAdapter(mViewAdapter)

        return view
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        Log.d(TAG, "onLoadFinished: swapCursor")
        data.let {
            mViewAdapter.swapCursor(data!!)
        }


    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        Log.d(TAG, "onLoaderReset: ")
        mViewAdapter.swapCursor(null)

    }


    override fun onEditClickListener(task: Task) {
        Log.d(TAG, "mainActivityFragment onEditClick: called")


        //listener here is MainActivity

        mListener = mactivity as OnTaskClickListener
        if (mListener != null) {
            Log.d(TAG, "mainActivityFragment onEditClick is calling MainActivity's onEditClick() method ")
            //calling MainActivity's onEditClick() method
            mListener!!.onEditClickListener(task)
        }
        Log.d(TAG, "onEditClickListener: ")

    }


    override fun onDeleteClick(task: Task) {
       mListener = mactivity as OnTaskClickListener


        Log.d(TAG, "onDeleteClick: ")
        if (mListener != null) {

            //calling MainActivity's onEditClick() method
            mListener!!.onDeleteClick(task)
        }


    }

    override fun onLongClickListener(task: Task) {
        Log.d(TAG, "onLongClickListener: ")
        mListener = mactivity as OnTaskClickListener
        if (mListener != null) {
            //calling MainActivity's onEditClick() method
            mListener!!.onLongClickListener(task)
        }
    }


}


