package com.example.myapplication

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class PlantCareAdapter(private val resources: Resources, private val onClick: (PlantCareNote) -> Unit) :
    ListAdapter<PlantCareNote, PlantCareAdapter.PlantCareHolder>(PlantCareDiffCallback) {
    private var randomVectorMap: HashMap<Int, Int> = hashMapOf()

    private val plantVectors: List<String> = listOf(
        "ic_cactus",
        "ic_cactus2",
        "ic_plantnote",
        "ic_plantnote2",
        "ic_soilwateringcan"
    )

    class PlantCareHolder(itemView: View, resources: Resources, plantVectors: List<String>, val onClick: (PlantCareNote) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val noteTitle: TextView = itemView.findViewById(R.id.pc_title)
        private val noteDate: TextView = itemView.findViewById(R.id.note_date)
        private val noteContent: TextView = itemView.findViewById(R.id.note)
        private val noteImg: ImageView = itemView.findViewById(R.id.randomImage)

        private val plantVectors: List<String> = plantVectors

        // Expose useful variables normally available only in Activity-derived classes.
        private val packageName = BuildConfig.APPLICATION_ID
        private val resources = resources

        private var currentNote: PlantCareNote? = null

        init {
            itemView.setOnClickListener {
                currentNote?.let {
                    onClick(it)
                }
            }
        }

        /* Use this function to populate the layout with actual values from
         the object retrieved from the database. */
        fun bind(plantCareNote: PlantCareNote, randomPlantVectorId: Int) {
            currentNote = plantCareNote

            val title = plantCareNote.title
            val id = plantCareNote.id
            val sdf = SimpleDateFormat("dd/MM/yyyy")

            var randomPlantVector = this.resources.getIdentifier(
                plantVectors[randomPlantVectorId], "drawable", this.packageName
            )

            noteTitle.text = "$title #$id"
            noteContent.text = plantCareNote.content
            noteDate.text = sdf.format(plantCareNote.date)
            noteImg.setImageResource(randomPlantVector)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantCareHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_care_item, parent, false)
        return PlantCareHolder(view, this.resources, this.plantVectors, onClick)
    }

    override fun onBindViewHolder(holder: PlantCareHolder, position: Int) {
        val plantCareNote = getItem(position)
        val previousRandomPlantVectorId: Int = this.randomVectorMap.get(position - 1) ?: -1
        var randomPlantVectorId: Int = this.randomVectorMap.get(position) ?: -1

        // Ensure that two consecutive icons are never the same.
        if (randomPlantVectorId < 0) {
            do {
                randomPlantVectorId = Random.nextInt(plantVectors.size)
            } while (randomPlantVectorId == previousRandomPlantVectorId)

            this.randomVectorMap.put(position, randomPlantVectorId)
        }

        holder.bind(plantCareNote, randomPlantVectorId)
    }
}

object PlantCareDiffCallback : DiffUtil.ItemCallback<PlantCareNote>() {
    override fun areItemsTheSame(oldItem: PlantCareNote, newItem: PlantCareNote): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PlantCareNote, newItem: PlantCareNote): Boolean {
        return oldItem.id == newItem.id
    }
}