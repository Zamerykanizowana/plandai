package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("HA", "Hello from classw!")
        setContentView(R.layout.home)

        val addNewPlantForm = findViewById<TextView>(R.id.textView4)
        addNewPlantForm.setOnClickListener {
            Log.i("HA", "Hello from listener!")
            setContentView(R.layout.add_new_plant)
        }
    }
}