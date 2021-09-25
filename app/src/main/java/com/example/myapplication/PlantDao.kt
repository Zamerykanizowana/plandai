package com.example.myapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY id ASC")
    fun getPlants(): List<Plant>

    @Query("SELECT * FROM plants WHERE id=:plantId LIMIT 1")
    fun getPlantById(plantId: Int): Plant

    @Insert
    suspend fun insert(plant: Plant) : Long

    @Update
    suspend fun update(plant: Plant)

    @Query("DELETE FROM plants WHERE id= :plantId")
    suspend fun delete(plantId: Int)
}