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
        startActivity(Intent(this, AddNewPlantActivity::class.java))
    }

    fun navigateToPlantCare(view: View) {
        startActivity(Intent(this, PlantCareActivity::class.java))
    }

    fun navigateToMyPlants(view: View) {
        startActivity(Intent(this, PlantActivity::class.java))
    }
}