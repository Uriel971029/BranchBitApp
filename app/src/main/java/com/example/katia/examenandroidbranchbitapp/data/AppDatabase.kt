package com.example.katia.examenandroidbranchbitapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.katia.examenandroidbranchbitapp.request.dto.EmployeeDTO

@Database(entities = [EmployeeDTO::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "app_database"
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        DB_NAME
                    ).build()
                }
            }

            return INSTANCE
        }

    }

    abstract fun employeeDao(): EmployeeDAO
}