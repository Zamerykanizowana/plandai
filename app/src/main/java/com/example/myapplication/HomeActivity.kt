package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log
import android.view.View

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
    }

    fun addNewPlant(view: View) {
        Log.i(this.localClassName, "In addNewPlant function")
        startActivity(Intent(this, AddNewPlantActivity::class.java))
    }

    fun navigateToPlantCare(view: View) {
        Log.i(this.localClassName, "In navigateToPlantCare function")
        startActivity(Intent(this, PlantCareActivity::class.java))
    }

    fun navigateToMyPlants(view: View) {
        Log.i(this.localClassName, "In navigateToMyPlants function")
        startActivity(Intent(this, PlantActivity::class.java))
    }

    fun addNewPlantCareNote(view: View) {
        Log.i(this.localClassName, "In addNewPlantCareNote function")
        startActivity(Intent(this, AddNewPlantCareActivity::class.java))
    }
}