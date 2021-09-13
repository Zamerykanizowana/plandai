package com.example.myapplication

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class AddNewPlantActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_plant)
        val dateField = findViewById<EditText>(R.id.editTextDate)
        dateField.setHint("Set date of purchase or planting --/--/--")

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                dateField.setText(sdf.format(cal.getTime()))
            }
        }
        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        dateField.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@AddNewPlantActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
//TODO Add spinner hint
        val mySpinner = findViewById<Spinner>(R.id.spinner1)


    }


    fun addNewPlant(view: View) {
        val name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val family = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
        val dateOfPurchase = findViewById<EditText>(R.id.editTextDate).text.toString()
        val size = findViewById<Spinner>(R.id.spinner1).selectedItem.toString()
        Log.i("anp", "addnewplant check : $size")
    }


    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}