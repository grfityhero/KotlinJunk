package com.elimalki.mykotlinapptest

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.elimalki.mykotlinapptest.AppProvider.Companion.CONTENT_URI

/**
 *Created by Eli Malki on 11/12/2018
 *
 */
class AddEDitActivityFragment : Fragment() {
    private enum class FragmentEditMode {
        ADD, EDIT
    }


    private val TAG = "AddEDitActivityFragment"
    private var mMode: FragmentEditMode? = null
    lateinit var mNameTextView: EditText
    lateinit var mDescriptionTextView: EditText
    lateinit var mSortOrderTextView: EditText
    lateinit private var mSaveButton: Button
    private var mSaveListener: OnSavedClicked? = null

     interface OnSavedClicked {
        fun OnSavedClicked()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_add_edit, container, false)
        mNameTextView = view.findViewById(R.id.addedit_name)
        mDescriptionTextView = view.findViewById(R.id.addedit_description)
        mSortOrderTextView = view.findViewById(R.id.addedit_sortorder)
        mSaveButton = view.findViewById(R.id.addedit_save)

        val arguments = arguments
        val task: Task?
        //get task object and see what mode :Add or Edit?
        if (arguments != null) {                    //   ↓  could put any string but its a good practice
            task = arguments.getSerializable(Task::class.java.simpleName) as Task
            if (task != null) {
                Log.d(TAG, "onCreateView: Task details found... editing")
                mNameTextView.setText(task.name)
                mDescriptionTextView.setText(task.description)
                mSortOrderTextView.setText(Integer.toString(task.sortOrder))

                mMode = FragmentEditMode.EDIT
            } else { //task==null
                // No task, so we must be adding a new task, and not editing an  existing one
                mMode = FragmentEditMode.ADD

            }

        } else {
            //no arguments so its a new task
            Log.d(TAG, "onCreateView: no task so add a new one")
            task = null
            mMode = FragmentEditMode.ADD


        }


        mSaveButton.setOnClickListener {
            //update the database if at least one field has changed.
            //no reason to hit the database unless that happens.
            val so: Int
            if (mSortOrderTextView.getText().length > 0) {
                so = Integer.parseInt(mSortOrderTextView.getText().toString())
            } else {
                so = 0
            }

            val contentResolver = context!!.contentResolver
            val values = ContentValues()


            when (mMode) {
                AddEDitActivityFragment.FragmentEditMode.EDIT -> {

                    task.let {
                        if (mNameTextView.getText().toString() != task?.name) {
                            values.put(TaskContract.Columns.TASKS_NAME, mNameTextView.getText().toString())
                        }
                        if (mDescriptionTextView.getText().toString() != task?.description) {
                            values.put(
                                TaskContract.Columns.TASKS_DESCRIPTION,
                                mDescriptionTextView.getText().toString()
                            )
                        }
                        if (so != task?.sortOrder) {
                            values.put(TaskContract.Columns.TASKS_SORTORDER, so)
                        }

                        //updating task
                        Log.d(TAG, "onClick: updating  existing task ")
                        if (values.size() > 0) {
                            contentResolver.update(TaskContract().buildTaskUri(task!!.id), values, null, null)
                        }
                    }
                }

                AddEDitActivityFragment.FragmentEditMode.ADD -> {
                    Log.d(TAG, "onClick: insert new task ")
                    if (mNameTextView.getText().length > 0) {
                        values.put(TaskContract.Columns.TASKS_NAME, mNameTextView.getText().toString())
                        values.put(TaskContract.Columns.TASKS_DESCRIPTION, mDescriptionTextView.getText().toString())
                        values.put(TaskContract.Columns.TASKS_SORTORDER, so)
                        contentResolver.insert(CONTENT_URI, values)
                    }
                }
            }

            Log.d(TAG, "onClick: Done Editing")


            if (mSaveListener != null) {
                mSaveListener?.OnSavedClicked()
            }
        }

        return view

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        //activities containing this fragment must implement its callbacks
        val activity = activity
        if (activity !is OnSavedClicked) {
            if (activity != null) {
                throw ClassCastException("ERROR:" + activity.javaClass.simpleName + " must implement OnSavedClicked")
            }
        }
        mSaveListener = activity as OnSavedClicked?
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onDetach() {
        Log.d(TAG, "onDetach: ")
        super.onDetach()
        mSaveListener = null
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)

    }
}