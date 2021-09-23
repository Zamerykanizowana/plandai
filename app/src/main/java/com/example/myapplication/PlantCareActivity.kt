package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate.now
import java.util.*

class PlantCareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plant_care)

        val plantCareAdapter = PlantCareAdapter(this.resources) { plantCareNote -> adapterOnClick(plantCareNote) }
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter = plantCareAdapter

        val plantDb = PlantRoomDatabase.getDatabase(this)

        lifecycleScope.launch {

//            for (i in 1..5) {
//                val testNote = PlantCareNote(
//                    0,
//                    "Cool note",
//                    SimpleDateFormat("dd/MM/yyyy").parse("11/07/1999"),
//                    "Some random content"
//                )
//                plantDb.plantCareNoteDao().insert(testNote)
//            }
            plantCareAdapter.submitList(plantDb.plantCareNoteDao().getNotes())
        }
    }

    private fun adapterOnClick(plantCareNote: PlantCareNote) {
        val logItem = plantCareNote.id
        Log.i(this.localClassName, "$logItem")
    }

    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}