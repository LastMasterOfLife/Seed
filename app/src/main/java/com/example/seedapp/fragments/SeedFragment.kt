package com.example.seedapp.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.seedapp.Data.TreeView
import com.example.seedapp.R
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class SeedFragment : Fragment() {

    /*
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var isDragging = false

    // Dimensione minima del pianeta
    private val MIN_PLANET_SIZE = 300 // Imposta la dimensione minima desiderata
    private val MIN_SCALE_FACTOR = 0.5f // Fattore di scala minimo (50%)
    private val MAX_SCALE_FACTOR = 2.0f // Fattore di scala massimo (200%)

    // Definizione delle dimensioni minime
    private val MIN_WIDTH = 70f
    private val MIN_HEIGHT = 1192f

     */

    private lateinit var treeView: TreeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_seed, container, false)
       // val spaceContainer: FrameLayout = view.findViewById(R.id.spaceContainer)
       // val addButton: Button = view.findViewById(R.id.addButton)

        treeView = view.findViewById(R.id.treeView)
        val addBranchButton: Button = view.findViewById(R.id.addBranchButton)

        addBranchButton.setOnClickListener {
            treeView.incrementBranchLength() // Chiamata al metodo per aggiungere un ramo
        }

        /*
        // Configurazione del rilevatore di zoom e trascinamento
        scaleGestureDetector = ScaleGestureDetector(requireContext(), object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                // Calcolo del nuovo fattore di scala
                val newScaleFactor = scaleFactor * detector.scaleFactor
                // Limita il fattore di scala
                scaleFactor = max(MIN_SCALE_FACTOR, min(newScaleFactor, MAX_SCALE_FACTOR))

                // Calcola le nuove dimensioni del contenitore
                val newWidth : Float = spaceContainer.width * scaleFactor
                val newHeight = spaceContainer.height * scaleFactor

                // Applica le restrizioni sulle dimensioni
                spaceContainer.layoutParams.width = newWidth.coerceAtLeast(MIN_WIDTH.toInt().toFloat()).toInt()
                spaceContainer.layoutParams.height = newHeight.coerceAtLeast(MIN_HEIGHT.toInt().toFloat()).toInt()

                // Imposta la scala sul contenitore
                spaceContainer.scaleX = scaleFactor
                spaceContainer.scaleY = scaleFactor

                return true
            }
        })

        // Gestisce lo zoom e il drag

        spaceContainer.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!scaleGestureDetector.isInProgress) { // Solo se non si sta zoomando
                        lastTouchX = event.x / scaleFactor
                        lastTouchY = event.y / scaleFactor
                        isDragging = true
                        spaceContainer.performClick()
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (isDragging) {
                        val touchX = event.x / scaleFactor // currentScaleFactor è il tuo fattore di zoom attuale
                        val touchY = event.y / scaleFactor

                        val deltaX = touchX - lastTouchX
                        val deltaY = touchY - lastTouchY

                        spaceContainer.translationX += deltaX
                        spaceContainer.translationY += deltaY

                        lastTouchX = touchX
                        lastTouchY = touchY

                        Log.d("TOUCH_DELTA", "$deltaX - $deltaY")
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isDragging = false
                }
            }
            true
        }

        addButton.setOnClickListener {
            showInputDialog("Inserisci il titolo del pianeta") { title ->
                val planetImageRes = R.drawable.albero_pianeta
                addNewPlanet(planetImageRes, title)
            }
        }

         */

        return view
    }

    /*
    // Creazione del nuovo albero con immagine e testo
    private fun createPlanet(planetImageRes: Int, planetTitle: String): FrameLayout {
        val textSize = planetTitle.length * 10 // Calcola la dimensione del pianeta in base alla lunghezza del testo
        val planetSize = (textSize + 50).coerceAtLeast(MIN_PLANET_SIZE) // Dimensione minima di 300

        val planetContainer = FrameLayout(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(planetSize, planetSize)
        }

        val planetImage = ImageView(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setImageResource(planetImageRes)
            scaleType = ImageView.ScaleType.CENTER_CROP
            background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_shape)
        }

        val planetTitleView = TextView(requireContext()).apply {
            text = planetTitle
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            )
            setTextColor(Color.BLACK)
            setPadding(0, 0, 0, 16)
        }

        planetContainer.addView(planetImage)
        planetContainer.addView(planetTitleView)
        planetContainer.alpha = 0f
        planetContainer.scaleX = 0f
        planetContainer.scaleY = 0f
        planetContainer.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(500)
            .start()

        return planetContainer
    }

    // Aggiunta del nuovo albero
    private fun addNewPlanet(planetImageRes: Int, planetTitle: String): FrameLayout {
        val spaceContainer = view?.findViewById<FrameLayout>(R.id.spaceContainer)
        val newPlanet = createPlanet(planetImageRes, planetTitle)

        // Posizionamento casuale all'interno del contenitore, senza sovrapposizione
        var isOverlapping: Boolean
        var randomX: Int
        var randomY: Int

        val addButton = view?.findViewById<Button>(R.id.addButton)
        val addButtonRect = Rect()
        addButton?.getGlobalVisibleRect(addButtonRect) // Ottieni il rettangolo del pulsante

        do {
            randomX = Random.nextInt(0, (spaceContainer?.width ?: 0) - newPlanet.layoutParams.width)
            randomY = Random.nextInt(0, (spaceContainer?.height ?: 0) - newPlanet.layoutParams.height)
            newPlanet.translationX = randomX.toFloat()
            newPlanet.translationY = randomY.toFloat()

            isOverlapping = false

            // Controllo sovrapposizione con tutti i pianeti esistenti
            for (i in 0 until (spaceContainer?.childCount ?: 0)) {
                val existingPlanet = spaceContainer?.getChildAt(i)
                if (existingPlanet != null && existingPlanet != newPlanet) {
                    // Assicurati di non confrontare con se stesso
                    if (isOverlapping(newPlanet, addButtonRect)) {
                        isOverlapping = true
                        break
                    }
                }
            }

            // Controllo sovrapposizione con il pulsante di aggiunta
            if (isOverlapping(newPlanet, addButtonRect)) {
                isOverlapping = true // Se sovrappone al pulsante, segnala sovrapposizione
            }

        } while (isOverlapping)

        spaceContainer?.addView(newPlanet)
        return newPlanet
    }

    // Controlla la sovrapposizione tra il button e il nuovo albero e tra il nuovo albero
    // e tutti gli alberi che sono contenuti nel container
    private fun isOverlapping(planet: View, buttonRect: Rect): Boolean {
        val location = IntArray(2)
        planet.getLocationOnScreen(location)

        val rect1 = Rect(location[0], location[1], location[0] + planet.width, location[1] + planet.height)
        return Rect.intersects(rect1, buttonRect) // Controlla sovrapposizione con il rettangolo del pulsante
    }

    // Creazione della finestra di dialogo con input il testo come titolo dell'albero
    private fun showInputDialog(title: String, onTitleEntered: (String) -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)

        val input = EditText(requireContext())
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            val goalTitle = input.text.toString()
            onTitleEntered(goalTitle)
        }

        builder.setNegativeButton("Annulla") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

     */
}
