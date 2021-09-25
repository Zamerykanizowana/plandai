package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
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

        var onOpenSpinner = false
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
                setBagroundTintEditText(dateField, validationCheckEditText(dateField))
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
//                really stupid way to check if this is form's first open
                if(!onOpenSpinner) {
                    setBagroundTintSpinner(mySpinner, onOpenSpinner)
                    onOpenSpinner = true
                } else {
                    setBagroundTintSpinner(mySpinner, validationCheckSpinner(mySpinner))
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
//                nothing
            }
        }
    }


    fun addNewPlant(view: View) {
        val name = findViewById<EditText>(R.id.editTextTextPersonName)
        val family = findViewById<EditText>(R.id.editTextTextPersonName2)
//        val dateOfPurchase = SimpleDateFormat("dd/MM/yyyy").parse(findViewById<EditText>(R.id.editTextDate).text.toString())
        val dateOfPurchase = findViewById<EditText>(R.id.editTextDate)
        val size = findViewById<Spinner>(R.id.spinner1)

        val sizeString = size.selectedItem.toString()
        Log.w("check", "how sizeCheckWorks : $sizeString")

        setBagroundTintEditText(name, validationCheckEditText(name))
        setBagroundTintEditText(family, validationCheckEditText(family))
        setBagroundTintEditText(dateOfPurchase, validationCheckEditText(dateOfPurchase))
        setBagroundTintSpinner(size, validationCheckSpinner(size))

        if (!validationCheckEditText(name) && !validationCheckEditText(family) && !validationCheckEditText(dateOfPurchase) && !validationCheckSpinner(size)) {
            Log.i("check", "all editTexts are fulfil")
            val newPlant = Plant(0, name.text.toString(), family.text.toString(), SimpleDateFormat("dd/MM/yyyy").parse(dateOfPurchase.text.toString()), size.selectedItem.toString())
            val plantDb = PlantRoomDatabase.getDatabase(this)
            GlobalScope.launch(Dispatchers.Main) {
                val newRow = plantDb.plantDao().insert(newPlant)
                Log.i("id", "new plant : $newRow")

//            val toast = Toast.makeText(this@AddNewPlantActivity, "Added new plant with ID $newRow", Toast.LENGTH_SHORT)
                val toast = Toast.makeText(this@AddNewPlantActivity, "Your new plant is on board!", Toast.LENGTH_SHORT)
                toast.show()
            }
            goHome(view)
        } else {
            Log.i("check", "empty field in form")
            val toast = Toast.makeText(this@AddNewPlantActivity, "Invalid value(s)!", Toast.LENGTH_SHORT)
            toast.show()

            name.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
                if(validationCheckEditText(name) && !focus) {
                    setBagroundTintEditText(name, true)
                } else {
                    setBagroundTintEditText(name, false)
                }
            }

            family.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
                if(validationCheckEditText(family) && !focus) {
                    setBagroundTintEditText(family, true)
                } else {
                    setBagroundTintEditText(family, false)
                }
            }

            dateOfPurchase.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
                if(validationCheckEditText(dateOfPurchase)) {
                    setBagroundTintEditText(dateOfPurchase, true)
                } else {
                    setBagroundTintEditText(dateOfPurchase, false)
                }
            }

            size.onFocusChangeListener = View.OnFocusChangeListener { view, focus ->
                if(validationCheckSpinner(size) && !focus) {
                    setBagroundTintSpinner(size, true)
                } else {
                    setBagroundTintSpinner(size, false)
                }
            }

        }
    }

    //DONE validation checking if selectedItem is NOT 0 element in spinner adapter data list (foolproof)
    private fun validationCheckSpinner(spinner: Spinner): Boolean {
//        return spinner.selectedItem.toString().compareTo("Select plant size") == 0
        val checkFirstItem = spinner.adapter.getItem(0).toString()
        Log.i("check", "check if taking data from adapter works : $checkFirstItem")
        return spinner.selectedItem.toString().compareTo(spinner.adapter.getItem(0).toString()) == 0
    }

    private fun setBagroundTintSpinner(spinner: Spinner, boolean: Boolean) {
        if (boolean){
            spinner.getBackground().setColorFilter(resources.getColor(R.color.warning_red), PorterDuff.Mode.SRC_ATOP)
        } else {
            spinner.getBackground().setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
        }
    }

    private fun validationCheckEditText(editText: EditText): Boolean {
        return editText.text.toString().isNullOrEmpty()
    }

    private fun setBagroundTintEditText(editText: EditText, boolean: Boolean) {
        if (boolean){
            editText.getBackground().setColorFilter(resources.getColor(R.color.warning_red), PorterDuff.Mode.SRC_ATOP)
        } else {
            editText.getBackground().setColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
        }
    }


    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}