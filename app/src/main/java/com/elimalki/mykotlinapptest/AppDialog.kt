package com.elimalki.mykotlinapptest

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class AppDialog : AppCompatDialogFragment() {
    private var mDialogEvents: DialogEvents? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity!!)
        val arguments = arguments
        val dialogid: Int
        val messageString: String?
        var positiveStringID: Int
        var negativeStringID: Int

        if (arguments != null) {
            dialogid = arguments.getInt(DIALOG_ID)
            messageString = arguments.getString(DIALOG_MESSAGE)

            // String taskname = arguments.getString("taskName");

            if (dialogid == 0 || messageString == null) {
                throw IllegalArgumentException("must pas dialog_id and dialog_message")
            }

            positiveStringID = arguments.getInt(DIALOG_POSITIVE_RID)
            if (positiveStringID == 0) {
                positiveStringID = R.string.ok
            }
            negativeStringID = arguments.getInt(DIALOG_NEGATIVE_RID)
            if (negativeStringID == 0) {
                negativeStringID = R.string.cancel
            }
        } else {
            throw IllegalArgumentException("must pas dialog_id and dialog_message")
        }


        builder.setMessage(messageString)
            .setPositiveButton(positiveStringID) { dialog, which ->
                if (mDialogEvents != null) {
                    mDialogEvents!!.OnPositiveDialogResult(dialogid, arguments)
                }
            }
        if (dialogid != 3) {//ok only

            builder.setNegativeButton(negativeStringID) { dialog, which ->
                if (mDialogEvents != null) {
                    mDialogEvents!!.OnNegativeDialogResult(dialogid, arguments)
                }
            }
        }

        return builder.create()
    }

    override fun onCancel(dialog: DialogInterface?) {
        Log.d(TAG, "onCancel: ")
        if (mDialogEvents != null) {
            var dialogId = 0
            if (arguments != null) {
                dialogId = arguments!!.getInt(DIALOG_ID)
            }
            mDialogEvents!!.OnDialogCancelled(dialogId)
        }
    }


    override fun onAttach(context: Context?) {
        Log.d(TAG, "onAttach: activity is:  " + context!!.toString())
        super.onAttach(context)
        if (context !is DialogEvents) {
            throw ClassCastException("Error: class must implement DialogEvents interface")
        } else {
            mDialogEvents = context
        }
    }

    override fun onDetach() {
        Log.d(TAG, "onDetach: ")
        super.onDetach()
        mDialogEvents = null

    }

    internal interface DialogEvents {
        fun OnPositiveDialogResult(dialogID: Int, args: Bundle?)

        fun OnNegativeDialogResult(dialogID: Int, args: Bundle?)

        fun OnDialogCancelled(dialogID: Int)
    }

    // Fragment Lifecycle callback events - added for logging only
    override fun onInflate(context: Context?, attrs: AttributeSet, savedInstanceState: Bundle) {
        Log.d(TAG, "onInflate: called")
        super.onInflate(context, attrs, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        Log.d(TAG, "onHiddenChanged: called")
        super.onHiddenChanged(hidden)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: called")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated: called")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewStateRestored: called")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG, "onStart: called")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "onResume: called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "onPause: called")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: called")
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: called")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: called")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        super.onDestroy()
    }

    companion object {
        val DIALOG_ID = "id"
        val DIALOG_MESSAGE = "Message"
        val DIALOG_POSITIVE_RID = "positive_rid"
        val DIALOG_NEGATIVE_RID = "negative_rid"
        private val TAG = "AppDialog"
    }

}