package com.example.seedapp.Data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.seedapp.R

class GoalsAdapter(private val items: List<Item>) : RecyclerView.Adapter<GoalsAdapter.ItemViewHolder>() {


    // ViewHolder che gestisce il layout di un singolo elemento
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.itemTextView)
    }

    // Inflating del layout di un elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_goals_layout, parent, false)
        return ItemViewHolder(view)
    }

    // Binding dei dati a un elemento
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = items[position].text
    }

    // Numero totale di elementi
    override fun getItemCount() = items.size
}
