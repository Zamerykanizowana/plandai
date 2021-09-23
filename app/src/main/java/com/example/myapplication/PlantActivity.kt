package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class PlantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plants)

        val plantAdapter = PlantAdapter { plant -> adapterOnClick(plant) }
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter = plantAdapter

        val plantDb = PlantRoomDatabase.getDatabase(this)

        lifecycleScope.launch {

//            for (i in 1..5) {
//                val testPlant = Plant(
//                    0,
//                    "Hoya",
//                    "Same random family",
//                    SimpleDateFormat("dd/MM/yyyy").parse("28/08/1997"),
//                    "Baby"
//                )
//                plantDb.plantDao().insert(testPlant)
//            }
            plantAdapter.submitList(plantDb.plantDao().getPlants())
        }
    }

    private fun adapterOnClick(plant: Plant) {
        val logItem = plant.id
        Log.i(this.localClassName, "$logItem")
    }

    fun goHome(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}