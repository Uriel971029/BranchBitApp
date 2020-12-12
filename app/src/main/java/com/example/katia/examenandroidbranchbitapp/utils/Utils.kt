package com.example.katia.examenandroidbranchbitapp.utils

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.katia.examenandroidbranchbitapp.home.HomeActivity
import com.google.android.material.snackbar.Snackbar

class Utils {
    companion object{
        fun navigateHome(context: Context){
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        fun showMessage(view: View, message: String){
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
        }

    }

}