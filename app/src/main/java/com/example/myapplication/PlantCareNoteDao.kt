package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface PlantCareNoteDao {
    @Query("SELECT * FROM plant_care_notes ORDER BY id ASC")
    suspend fun getNotes(): List<PlantCareNote>

    @Query("SELECT * FROM plant_care_notes WHERE id=:noteId LIMIT 1")
    fun getNoteById(noteId: Int): PlantCareNote

    @Insert
    suspend fun insert(plantCareNote: PlantCareNote) : Long

    @Update
    suspend fun update(plantCareNote: PlantCareNote)

    @Query("DELETE FROM plant_care_notes WHERE id= :noteId")
    suspend fun delete(noteId: Int)
}