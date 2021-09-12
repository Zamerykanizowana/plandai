package com.example.myapplication

import android.provider.ContactsContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey val id: Int,
    @ColumnInfo(name= "name") val name: String,
    @ColumnInfo(name= "family") val family: String?,
    @ColumnInfo(name= "date_of_purchase") val dop: Date,
    @ColumnInfo(name= "size") val size: String?
)