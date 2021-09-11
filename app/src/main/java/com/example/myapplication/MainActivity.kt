package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //changing view to home view by clicking button
        val startButton = findViewById<Button>(R.id.button)
        startButton.setOnClickListener {
            setContentView(R.layout.home)
        }


//        val addNewPlantForm = findViewById<TextView>(R.id.textView4)
//        addNewPlantForm.setOnClickListener {
//        setContentView(R.layout.add_new_plant)
//        }
    }
}