package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface PlantCareNoteDao {
    @Query("SELECT * FROM plant_care_notes ORDER BY id ASC")
    fun getNotes(): List<PlantCareNote>

    @Insert
    suspend fun insert(plantCareNote: PlantCareNote) : Long

    @Query("DELETE FROM plant_care_notes WHERE id= :noteId")
    suspend fun delete(noteId: Int)
}