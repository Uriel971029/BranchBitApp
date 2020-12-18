package com.example.katia.examenandroidbranchbitapp.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.katia.examenandroidbranchbitapp.utils.ContextApplication

object DatabaseSingleton {

    private val DB_NAME = "app_database"
    val db: RoomDatabase

    init {
         db = Room.databaseBuilder(
            ContextApplication.getContextApplication(),
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    fun getInstance(): RoomDatabase = db
}