package com.example.katia.examenandroidbranchbitapp.utils

import android.app.Application

class ContextApplication : Application() {
    companion object {
        lateinit var mApplication: ContextApplication

        open fun getContextApplication(): ContextApplication {
            return mApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }

}