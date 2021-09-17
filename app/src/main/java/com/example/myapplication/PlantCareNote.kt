package com.example.myapplication

import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "plant_care_notes")
data class PlantCareNote(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name= "title") val title: String,
    @ColumnInfo(name= "date_of_entry") val date: Date,
    @ColumnInfo(name= "content") val content: String?
)