package com.example.seedapp.Data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.seedapp.databinding.ItemSoundBinding

class SoundAdapter (private val sounds: List<String>, private val onSoundSelected: (String) -> Unit) : RecyclerView.Adapter<SoundAdapter.SoundViewHolder>(){

    inner class SoundViewHolder(val binding: ItemSoundBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sound: String) {
            binding.soundName.text = sound
            itemView.setOnClickListener { onSoundSelected(sound) }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
        val binding = ItemSoundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
        holder.bind(sounds[position])
    }

    override fun getItemCount(): Int = sounds.size
}