package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddNewPlantActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_plant)
        val context = this
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
        val mySpinner = findViewById<Spinner>(R.id.spinner1)

        // list of plant sizes
        val spinnerList = mutableListOf(
            "Baby",
            "S",
            "M",
            "L",
            "XL",
            "Monster"
        )

        // add a hint to spinner
        // list first item will show as hint
        spinnerList.add(0,"Select plant size")

        // initialize an array adapter for spinner
        val adapter:ArrayAdapter<String> = object: ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerList
        ){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view:TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView
                // set item text bold
//                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
//                if (position == mySpinner.selectedItemPosition && position !=0 ){
//                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
//                    view.setTextColor(Color.parseColor("#333399"))
//                }

                // make hint item color gray
                if(position == 0){
                    view.setTextColor(Color.parseColor("#203700"))
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25F)
                }


                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }


        // finally, data bind spinner with adapter
        mySpinner.adapter = adapter


        // spinner on item selected listener
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // nothing
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }


    }


    fun addNewPlant(view: View) {
        val name = findViewById<EditText>(R.id.editTextTextPersonName).text.toString()
        val family = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
        val dateOfPurchase = SimpleDateFormat("dd/MM/yyyy").parse(findViewById<EditText>(R.id.editTextDate).text.toString())
        val size = findViewById<Spinner>(R.id.spinner1).selectedItem.toString()
        Log.i("anp", "addnewplant check : $size")
        val newPlant = Plant(0, name, family, dateOfPurchase, size)
        val plantDb = PlantRoomDatabase.getDatabase(this)

        GlobalScope.launch(Dispatchers.Main) {
            val newRow = plantDb.plantDao().insert(newPlant)
            Log.i("id", "new plant : $newRow")

//            val toast = Toast.makeText(this@AddNewPlantActivity, "Added new plant with ID $newRow", Toast.LENGTH_SHORT)
            val toast = Toast.makeText(this@AddNewPlantActivity, "Your new plant is on board!", Toast.LENGTH_SHORT)
            toast.show()
        }

        goHome(view)
    }


    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}