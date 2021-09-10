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
        val star_button = findViewById(R.id.button) as Button
        star_button.setOnClickListener {
            setContentView(R.layout.home)
        }

        val add_new_plant_form = findViewById(R.id.textView4) as TextView
        add_new_plant_form.setOnClickListener {
            setContentView(R.layout.add_new_plant)
        }
    }
}