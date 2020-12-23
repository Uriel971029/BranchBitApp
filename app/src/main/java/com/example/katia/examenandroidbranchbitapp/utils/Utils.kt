package com.example.katia.examenandroidbranchbitapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.katia.examenandroidbranchbitapp.home.HomeActivity
import com.example.katia.examenandroidbranchbitapp.utils.dialogs.DialogFragmentMessage
import com.google.android.material.snackbar.Snackbar

class Utils {


    interface OnMessagePressed {
        fun onMessagePositiveButtonPressed()
    }

    companion object{

        private lateinit var mListener : OnMessagePressed

        fun navigateHome(context: Context){
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        fun showMessage(view: View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }

        fun showAlert(context: Context, title: String, message: String, btnText1: String, btnText2: String){
            if(context is OnMessagePressed)
                mListener = context
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(btnText1)
            { dialog, wich ->
                mListener.onMessagePositiveButtonPressed()
            }
            builder.setNegativeButton(btnText2)
            {dialog, wich->
                dialog.dismiss()
            }
            builder.show()
        }

        fun setToolbar(activity: AppCompatActivity, toolbar: Toolbar, title: String, isEnable: Boolean){
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.title = title
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(isEnable)
            activity.supportActionBar?.setDisplayShowHomeEnabled(isEnable)
        }
    }
}