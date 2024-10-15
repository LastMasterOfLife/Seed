package com.example.seedapp.Data
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
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
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SuoneriePrefs", Context.MODE_PRIVATE)

    
    class ButtonViewHolder(val binding: ItemButtonSuoneriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val binding = ItemButtonSuoneriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val buttonText = buttonList[position]
        val soundResource = soundList[position]
        holder.binding.playWistley.text = buttonText

        // Verifica se questa è la suoneria predefinita salvata
        val savedSoundResource = sharedPreferences.getInt("selected_ringtone", -1)
        holder.binding.myRadioButton.isChecked = (savedSoundResource == soundResource)

        if (savedSoundResource == soundResource) {
            // Cambia aspetto o testo per mostrare che è selezionato
            holder.binding.playWistley.text = "$buttonText (Predefinita)"
        }

        holder.binding.playWistley.setOnClickListener {
            mediaPlayer?.release()
            // Crea un nuovo MediaPlayer per riprodurre il suono corrispondente

            mediaPlayer = MediaPlayer.create(context, soundResource)
            mediaPlayer?.start()

            // Salva l’ID della suoneria selezionata nelle SharedPreferences
            sharedPreferences.edit().putInt("selected_ringtone", soundResource).apply()
            // Salva il testo del button selezionato nelle SharedPreferences
            sharedPreferences.edit().putString("selected_button_text", buttonText).apply()

            // Invia un Broadcast per ricreare tutte le Activity
            val intent = Intent("com.example.seedapp.RECREATE_ALL_ACTIVITIES")
            context.sendBroadcast(intent)

            // Aggiorna i RadioButton
            notifyDataSetChanged()  // Aggiorna l'intera lista per mostrare il RadioButton selezionato

        }

        // Imposta il listener per il RadioButton (opzionale, se vuoi selezionarlo anche dal RadioButton)
        holder.binding.myRadioButton.setOnClickListener {
            // Salva l'ID della suoneria selezionata quando il RadioButton viene selezionato
            sharedPreferences.edit().putInt("selected_ringtone", soundResource).apply()
            notifyDataSetChanged()
        }

    }



    override fun getItemCount(): Int = buttonList.size

    // Libera MediaPlayer quando non è più necessario
    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }


}