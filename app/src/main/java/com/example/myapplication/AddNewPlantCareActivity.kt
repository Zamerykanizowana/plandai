package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
                validationCheck(dateField)
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
        val noteTitle = findViewById<EditText>(R.id.noteTitle)
        val description = findViewById<EditText>(R.id.description)
        val dateOfCare = findViewById<EditText>(R.id.dateOfCare)

        validationCheck(noteTitle)
        validationCheck(description)
        validationCheck(dateOfCare)

        if (!noteTitle.text.toString().isNullOrEmpty() && !description.text.toString().isNullOrEmpty() && !dateOfCare.text.toString().isNullOrEmpty()) {
            Log.i("anpcn", "addNewPlantCareNote check : $noteTitle")
            val parsedDateCare = SimpleDateFormat("dd/MM/yyyy").parse(dateOfCare.text.toString())
            val newCareNote = PlantCareNote(0, noteTitle.text.toString(), parsedDateCare, description.text.toString())
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
//            validationCheck(noteTitle)
//            validationCheck(description)
//            validationCheck(dateOfCare)
            val toast = Toast.makeText(this@AddNewPlantCareActivity, "Invalid value(s)!", Toast.LENGTH_SHORT)
            toast.show()

            noteTitle.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
                if(noteTitle.text.toString().isNullOrEmpty() && !focus) {
                    setWarningBackgroudTind(noteTitle)
                } else {
                    setNormalBackgroudTind(noteTitle)
                }
            }

            dateOfCare.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
                if(dateOfCare.text.toString().isNullOrEmpty()) {
                    setWarningBackgroudTind(dateOfCare)
                } else {
                    setNormalBackgroudTind(dateOfCare)
                }
            }

            description.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
                if(description.text.toString().isNullOrEmpty() && !focus) {

                    setWarningBackgroudTind(description)
                } else {
                    setNormalBackgroudTind(description)
                }
            }
        }
    }

    private fun validationCheck(editText: EditText) {
        if (editText.text.toString().isNullOrEmpty()) {
            Log.i("warning", "empty editText : $editText")
            setWarningBackgroudTind(editText)
        } else {

            setNormalBackgroudTind(editText)
        }
    }

    private fun setWarningBackgroudTind(editText: EditText) {
        editText.getBackground().setColorFilter(resources.getColor(R.color.warning_red), PorterDuff.Mode.SRC_ATOP);
    }

    private fun setNormalBackgroudTind(editText: EditText) {
        editText.getBackground().setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
    }

    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}

