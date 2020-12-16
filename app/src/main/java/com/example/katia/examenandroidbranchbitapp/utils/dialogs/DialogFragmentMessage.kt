package com.example.katia.examenandroidbranchbitapp.utils.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.katia.examenandroidbranchbitapp.R

class DialogFragmentMessage : DialogFragment() {

    interface OnDialogFragmentMessagePressed {
        fun onMessagePositiveButtonPressed()
    }

    companion object {

        private const val DIALOG_TITLE = "title"
        private const val DIALOG_MESSAGE = "message"
        private const val DIALOG_BTNTEXT1 = "btnText1"
        private const val DIALOG_BTNTEXT2 = "btnText2"
        private lateinit var mActivity: Activity
        private lateinit var mListener : OnDialogFragmentMessagePressed

        fun newInstance(activity: Activity, title: String, message: String, btnText1: String, btnText2: String): DialogFragment {
            val args = Bundle()
            mActivity = activity
            if(activity is OnDialogFragmentMessagePressed)
                mListener = activity
            args.putString(DIALOG_TITLE, title)
            args.putString(DIALOG_MESSAGE, message)
            args.putString(DIALOG_BTNTEXT1, btnText1)
            args.putString(DIALOG_BTNTEXT2, btnText2)
            val fragment = DialogFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(arguments?.getString(DIALOG_TITLE))
        builder.setMessage(arguments?.getString(DIALOG_MESSAGE))
        builder.setPositiveButton(arguments?.getString(DIALOG_BTNTEXT1))
        { dialog, wich ->
            mListener.onMessagePositiveButtonPressed()
        }
        builder.setNegativeButton(arguments?.getString(DIALOG_BTNTEXT2))
        {dialog, wich->
            dismiss()
        }
        return builder.create()
    }


}