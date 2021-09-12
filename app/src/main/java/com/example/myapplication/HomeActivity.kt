package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val addNewPlantForm = findViewById<TextView>(R.id.textView4)
        addNewPlantForm.setOnClickListener {
            setContentView(R.layout.add_new_plant)
        }
    }
}