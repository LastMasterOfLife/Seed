package com.example.seedapp.Data
import android.content.Context
import android.media.MediaPlayer
import com.example.seedapp.databinding.ItemButtonSuoneriaBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.seedapp.R

class SuoneeriaAdapter(private val context: Context, private val buttonList: List<String>, private val soundList: List<Int>) : RecyclerView.Adapter<SuoneeriaAdapter.ButtonViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null

    
    class ButtonViewHolder(val binding: ItemButtonSuoneriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val binding = ItemButtonSuoneriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val buttonText = buttonList[position]
        val soundResource = soundList[position]
        holder.binding.playWistley.text = buttonText
        holder.binding.playWistley.setOnClickListener {
            mediaPlayer?.release()
            // Crea un nuovo MediaPlayer per riprodurre il suono corrispondente

            mediaPlayer = MediaPlayer.create(context, soundResource)
            mediaPlayer?.start()
        }
    }

    override fun getItemCount(): Int = buttonList.size

    // Libera MediaPlayer quando non è più necessario
    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}