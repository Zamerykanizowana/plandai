package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddNewPlantCareActivity  : AppCompatActivity() {
    val cal = Calendar.getInstance()
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_care_note)
//        val context = this
        val dateField = findViewById<EditText>(R.id.dateOfCare)
        dateField.setHint("set date --/--/--")

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                dateField.setText(sdf.format(cal.getTime()))
            }
        }
        dateField.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@AddNewPlantCareActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })
    }

    fun addNewPlantCareNote(view: View) {
        val noteTitle = findViewById<EditText>(R.id.noteTitle).text.toString()
        val description = findViewById<EditText>(R.id.description).text.toString()
//        TODO tu sie wykrzacza, nie moze przeparsowac pusta date!
        val dateOfCare = SimpleDateFormat("dd/MM/yyyy").parse(findViewById<EditText>(R.id.dateOfCare).text.toString())
        Log.i("test", "value in new note : $noteTitle")
        Log.i("test", "value in new note : $description")
        Log.i("test", "value in new note : $dateOfCare")
        if (!noteTitle.isNullOrEmpty() && !description.isNullOrEmpty()) {
            Log.i("anpcn", "addNewPlantCareNote check : $noteTitle")
            val newCareNote = PlantCareNote(0, noteTitle, dateOfCare, description)
            val plantDb = PlantRoomDatabase.getDatabase(this)

            GlobalScope.launch(Dispatchers.Main) {
                val newRow = plantDb.plantCareNoteDao().insert(newCareNote)
                Log.i("id", "new plant care note : $newRow")

//            val toast = Toast.makeText(this@AddNewPlantActivity, "Added new plant with ID $newRow", Toast.LENGTH_SHORT)
                val toast = Toast.makeText(this@AddNewPlantCareActivity, "Your new plant care note is added!", Toast.LENGTH_SHORT)
                toast.show()
            }

            goHome(view)
        } else {
            Log.i("warning", "empty values in form")
            val toast = Toast.makeText(this@AddNewPlantCareActivity, "Invalid value(s)!", Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}

