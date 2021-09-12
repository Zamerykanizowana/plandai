package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.security.AccessControlContext

@Database(entities = arrayOf(Plant::class), version = 1, exportSchema = false)
public abstract class PlantRoomDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    companion object {
        @Volatile
        private var INSTANCE: PlantRoomDatabase? = null
        fun getDatabase(context: Context): PlantRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, PlantRoomDatabase::class.java, "plants_databese"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}