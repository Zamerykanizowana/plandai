package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY id ASC")
    fun getPlants(): List<Plant>

    @Insert
    suspend fun insert(plant: Plant)

    @Query("DELETE FROM plants WHERE id= :plantId")
    suspend fun delete(plantId: Int)
}