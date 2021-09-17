package com.example.myapplication

import android.content.Context
import androidx.room.*
import java.security.AccessControlContext

@Database(entities = arrayOf(Plant::class, PlantCareNote::class), version = 3, exportSchema = false)
@TypeConverters(DbConverters::class)
public abstract class PlantRoomDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    abstract fun plantCareNoteDao(): PlantCareNoteDao
    companion object {
        @Volatile
        private var INSTANCE: PlantRoomDatabase? = null
        fun getDatabase(context: Context): PlantRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, PlantRoomDatabase::class.java, "plants_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
