package com.example.myapplication

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

class PlantCareAdapter(private val onClick: (PlantCareNote) -> Unit) :
    ListAdapter<PlantCareNote, PlantCareAdapter.PlantCareHolder>(PlantCareDiffCallback) {
    class PlantCareHolder(itemView: View, val onClick: (PlantCareNote) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val noteTitle: TextView = itemView.findViewById(R.id.pc_title)
        private val noteDate: TextView = itemView.findViewById(R.id.note_date)
        private val noteContent: TextView = itemView.findViewById(R.id.note)
        
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantCareHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_care_item, parent, false)
        return PlantCareHolder(view, onClick)
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