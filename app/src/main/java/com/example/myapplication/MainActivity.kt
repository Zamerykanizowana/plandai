package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //changing view to home view by clicking button
        val startButton = findViewById<Button>(R.id.button)
        startButton.setOnClickListener {

//            example toast
//            Toast.makeText(this, "Hello, I am Toast!", Toast.LENGTH_LONG).show()
            //setContentView(R.layout.home)
            val goHomeIntent = Intent(this, HomeActivity::class.java)
            startActivity(goHomeIntent)
        }
    }
}