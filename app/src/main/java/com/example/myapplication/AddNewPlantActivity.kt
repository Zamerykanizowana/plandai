package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log
import android.view.View
import android.widget.EditText

class AddNewPlantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_plant)
    }

    fun addNewPlant(view: View) {
        val name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val family = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
        val dateOfPurchase = findViewById<EditText>(R.id.editTextDate).text.toString()
        Log.i("anp", "addnewplant check : $name")
    }


    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}