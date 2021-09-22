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

class PlantAdapter(private val onClick: (Plant) -> Unit) :
    ListAdapter<Plant, PlantAdapter.PlantHolder>(PlantDiffCallback) {
    class PlantHolder(itemView: View, val onClick: (Plant) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val plantName: TextView = itemView.findViewById(R.id.plant_name)
        private val purchaseDate: TextView = itemView.findViewById(R.id.purchase_date)
        private val plantContent: TextView = itemView.findViewById(R.id.family)

        private var currentPlant: Plant? = null

        init {
            itemView.setOnClickListener {
                currentPlant?.let {
                    onClick(it)
                }
            }
        }

        /* Use this function to populate the layout with actual values from
         the object retrieved from the database. */
        fun bind(plant: Plant) {
            currentPlant = plant

            val name = plant.name
            val id = plant.id
            val sdf = SimpleDateFormat("dd/MM/yyyy")

            plantName.text = "$name #$id"
            plantContent.text = plant.family
            purchaseDate.text = sdf.format(plant.dop)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_item, parent, false)
        return PlantHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PlantHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant)
    }
}

object PlantDiffCallback : DiffUtil.ItemCallback<Plant>() {
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.id == newItem.id
    }
}