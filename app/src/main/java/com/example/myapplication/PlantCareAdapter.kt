package com.example.myapplication

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import kotlin.coroutines.coroutineContext

class PlantCareAdapter(private val resources: Resources, private val onClick: (PlantCareNote) -> Unit) :
    ListAdapter<PlantCareNote, PlantCareAdapter.PlantCareHolder>(PlantCareDiffCallback) {
    class PlantCareHolder(itemView: View, resources: Resources, val onClick: (PlantCareNote) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val noteTitle: TextView = itemView.findViewById(R.id.pc_title)
        private val noteDate: TextView = itemView.findViewById(R.id.note_date)
        private val noteContent: TextView = itemView.findViewById(R.id.note)
        private val noteImg: ImageView = itemView.findViewById(R.id.randomImage)

        private val plantVectors: List<String> = listOf(
            "ic_cactus",
            "ic_cactus2",
            "ic_plantnote",
            "ic_plantnote2",
            "ic_plantnote3",
            "ic_soilwateringcan"
        )

        private val packageName = BuildConfig.APPLICATION_ID

        private val randomPlantVector = resources.getIdentifier(plantVectors.random(), "drawable", this.packageName)

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
        fun bind(plantCareNote: PlantCareNote) {
            currentNote = plantCareNote

            val title = plantCareNote.title
            val id = plantCareNote.id
            val sdf = SimpleDateFormat("dd/MM/yyyy")

            noteTitle.text = "$title #$id"
            noteContent.text = plantCareNote.content
            noteDate.text = sdf.format(plantCareNote.date)
            noteImg.setImageResource(randomPlantVector)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantCareHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_care_item, parent, false)
        return PlantCareHolder(view, this.resources, onClick)
    }

    override fun onBindViewHolder(holder: PlantCareHolder, position: Int) {
        val plantCareNote = getItem(position)
        holder.bind(plantCareNote)
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