package com.elimalki.mykotlinapptest

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

/**
 *Created by Eli Malki on 09/12/2018
 *
 */
private val TAG = "MyRecyclerViewAdapter"
 var mCursor: Cursor? = null
lateinit var mListener: OnTaskClickListener

interface OnTaskClickListener {
    fun onEditClickListener(task: Task)
    fun onDeleteClick(task: Task)
    fun onLongClickListener(task: Task)
}

class MyRecyclerViewAdapter(var dCursor: Cursor?, listener: OnTaskClickListener ) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

init {
    mListener = listener
    mCursor = dCursor

}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewAdapter.MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return MyRecyclerViewAdapter.MyViewHolder(view)

    }

    override fun onBindViewHolder(holderMy: MyViewHolder, position: Int) {

        if ((mCursor != null) || (mCursor?.count != null)) {
            //  mCursor.let {
           var test :String;

            while(mCursor!!.moveToNext()) {
                test = mCursor!!.getString(1);
                Log.d(TAG, "mCursor: " +test)
            }

            if (mCursor!=null) {
                if (!mCursor!!.moveToPosition(position)) {
                    throw IllegalArgumentException("Could not move cursor to position.")
                }

                val task: Task = Task(
                    mCursor!!.getLong(mCursor!!.getColumnIndex(TaskContract.Columns._ID))
                    , mCursor!!.getString(mCursor!!.getColumnIndex(TaskContract.Columns.TASKS_NAME))
                    , mCursor!!.getString(mCursor!!.getColumnIndex(TaskContract.Columns.TASKS_DESCRIPTION))
                    , mCursor!!.getInt(mCursor!!.getColumnIndex(TaskContract.Columns.TASKS_SORTORDER))
                )




                holderMy.tname.setText(task.name)
                holderMy.tDesc.setText(task.description)


                val buttonListener = View.OnClickListener { v ->
                    when (v.id) {

                        R.id.btn_edit -> {
                            mListener.onEditClickListener(task)
                           // Log.d(TAG, ":onEditClickListener ")
                        }
                        R.id.btn_delete -> {
                            mListener.onDeleteClick(task)
                            //Log.d(TAG, ":onEditClickListener ")
                        }
                    }
                }

                holderMy.editButton.setOnClickListener(buttonListener)
                holderMy.deleteButton.setOnClickListener(buttonListener)
                // holderMy.itemView.setOnLongClickListener(buttonLongListener)
            }
        }

    }


    override fun getItemCount(): Int {


        return if (mCursor == null || mCursor?.count == 0) {
            1
        } else {
            mCursor!!.count
        }
    }

    fun swapCursor(newCursor: Cursor?): Cursor? {

        (newCursor == mCursor) ?: null
//        if (newCursor === mCursor) {
//            return null
//        }

        val oldCursor = mCursor // mCursor -->> oldCursor

        newCursor.let {
            mCursor = newCursor!! // newCusor -->> mCursor


            if (newCursor != null) {
                //notify about new mCursor
                notifyDataSetChanged()
            } else { // newCursor is null
                //notify about lack of data set
                notifyItemRangeRemoved(0, itemCount)

            }
        }
        return oldCursor

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tname: TextView = view.findViewById(R.id.tv_task_name)
        val tDesc: TextView = view.findViewById(R.id.tv_task_description)
        val editButton: ImageButton = view.findViewById(R.id.btn_edit)
        val deleteButton: ImageButton = view.findViewById(R.id.btn_delete)
        val itemView: View = view

    }
}