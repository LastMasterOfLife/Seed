package com.example.seedapp.Data

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.animation.ValueAnimator
import androidx.compose.ui.Modifier
import kotlin.math.cos
import kotlin.math.sin

class TreeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.rgb(101, 67, 33) // Marrone per tronco e rami
        isAntiAlias = true
    }

    private var branchLength = 0f // Lunghezza attuale del ramo
    private val incrementValue = 150f // Valore da incrementare ad ogni clic sul pulsante
    private var shouldDrawBranches = false // Variabile per controllare il disegno dei rami

    // Variabili per il trascinamento
    private var previousX = 0f
    private var previousY = 0f
    private var translationX = 0f
    private var translationY = 0f

    // Variabili per lo zoom
    private var scaleFactor = 1f
    private val scaleGestureDetector: ScaleGestureDetector

    // Spessori per tronco, rami e sotto-rami
    private val trunkWidth = 40f // Spessore del tronco
    private val branchWidth = 25f // Spessore dei rami
    private val subBranchWidth = 8f // Spessore dei sotto-rami

    private val nodes = mutableListOf<Node>() // Lista dei nodi
    private var currentNodeIndex = -1 // Indice del nodo corrente

    data class Node(var isVisible: Boolean, val text: String)

    // Dati per la posizione dei nodi
    private val nodePositions = listOf(
        Pair(100f, 300f),  // Posizione del primo nodo
        Pair(200f, 400f),  // Posizione del secondo nodo
        Pair(150f, 500f),  // Posizione del terzo nodo
        Pair(250f, 600f),  // Posizione del quarto nodo
        Pair(200f, 700f),  // Posizione del quinto nodo
        Pair(300f, 800f),  // Posizione del sesto nodo
        Pair(250f, 900f)   // Posizione del settimo nodo
    )

    init {
        scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector?.scaleFactor ?: 1f
                scaleFactor = scaleFactor.coerceIn(0.1f, 5.0f) // Limita lo zoom
                invalidate() // Richiama il metodo onDraw per ridisegnare la vista
                return true
            }
        })

        // Inizializza i nodi (tutti invisibili)
        for (i in 0 until 7) { // Aggiungi 7 nodi (o più, se necessario)
            nodes.add(Node(false, "Nodo ${i + 1}"))
        }
    }

    fun showNextNode() {
        if (currentNodeIndex + 1 < nodes.size) {
            currentNodeIndex++ // Passa al prossimo nodo
            nodes[currentNodeIndex].isVisible = true
            invalidate() // Richiama il metodo onDraw per aggiornare la vista
        }
    }

    // Aggiungi nella classe TreeView
    private val nodePaint = Paint().apply {
        color = Color.BLUE // Colore del nodo
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 30f
        isAntiAlias = true
    }

    fun drawInitialNode() {
        shouldDrawBranches = true
        invalidate()
    }

    private val leafPaint = Paint().apply {
        color = 0xFF4CAF50.toInt() // Colore verde per le foglie
        style = Paint.Style.FILL
    }


    // Funzione per ottenere la posizione X del nodo
    private fun calculateNodeX(index: Int): Float {
        return nodePositions.getOrNull(index)?.first ?: 0f
    }

    // Funzione per ottenere la posizione Y del nodo
    private fun calculateNodeY(index: Int): Float {
        return nodePositions.getOrNull(index)?.second ?: 0f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save() // Salva lo stato corrente della canvas

        // Applica la trasformazione di zoom e traslazione
        canvas.translate(translationX, translationY)
        canvas.scale(scaleFactor, scaleFactor)

        leafCounter = 0
        val startX = width / 2f
        val startY = height.toFloat()
        val trunkHeight = 100f // Altezza totale del tronco
        val bottomWidth = 100f // Larghezza alla base del tronco
        val topWidth = 25f // Larghezza alla cima del tronco
        val trunkSegments = 100 // Numero di segmenti per creare l'effetto gradiente



        // Disegna il tronco a segmenti, diminuendo la larghezza verso l'alto
        for (i in 0 until trunkSegments) {
            // Calcola la posizione verticale per ogni segmento
            val segmentStartY = startY - (i * (trunkHeight / trunkSegments))
            val segmentEndY = startY - ((i + 1) * (trunkHeight / trunkSegments))

            // Calcola la larghezza per ogni segmento
            paint.strokeWidth = bottomWidth - i * ((bottomWidth - topWidth) / trunkSegments)

            // Disegna il segmento
            canvas.drawLine(startX, segmentStartY, startX, segmentEndY, paint)
        }

        // Disegna il ramo principale se abilitato
        if (shouldDrawBranches) {
            drawBranch(canvas, startX, startY - trunkHeight, -90.0, branchLength) // Disegna il ramo principale
        }


        // Disegna i nodi visibili
        for ((index, node) in nodes.withIndex()) {
            if (node.isVisible) {
                val x = calculateNodeX(index) // Funzione per calcolare la posizione X del nodo
                val y = calculateNodeY(index) // Funzione per calcolare la posizione Y del nodo
                drawNode(canvas, x, y, node.text)
            }
        }

        canvas.restore() // Ripristina lo stato della canvas
    }

    private fun drawNode(canvas: Canvas, x: Float, y: Float, text: String) {
        canvas.drawCircle(x, y, 30f, nodePaint) // Cerchio per il nodo
        canvas.drawRect(x - 40f, y + 20f, x + 40f, y + 60f, nodePaint) // Rettangolo con testo
        canvas.drawText(text, x - 35f, y + 50f, textPaint) // Testo del nodo
    }

    private fun drawBranch(canvas: Canvas, x1: Float, y1: Float, angle: Double, length: Float) {
        if (length < 10) return // Se il ramo è troppo corto, fermati

        // Calcola le coordinate del punto finale del ramo
        val x2 = x1 + (length * cos(Math.toRadians(angle))).toFloat()
        val y2 = y1 + (length * sin(Math.toRadians(angle))).toFloat()

        // Disegna il ramo principale
        paint.strokeWidth = branchWidth // Imposta spessore del ramo
        canvas.drawLine(x1, y1, x2, y2, paint)

        // Disegna le foglie alla fine del ramo
        drawLeaves(canvas, x2, y2)

        // Disegna i sotto-rami con angoli casuali
        drawSubBranches(canvas, x2, y2, angle, length)
    }

    private var leafCounter = 0 // Contatore per le prime sette foglie
    private val firstThreeLeafCoordinates = mutableListOf<Pair<Float, Float>>()


    private fun drawLeaves(canvas: Canvas, x: Float, y: Float) {
        if (leafCounter < 3) {
            // Salva le coordinate delle prime tre foglie
            firstThreeLeafCoordinates.add(Pair(x, y))

            // Disegna il cerchio come nodo
            canvas.drawCircle(x, y, 30f, nodePaint)

            // Disegna il rettangolino con il testo sotto il cerchio
            canvas.drawRect(
                x - 40f, y + 20f,
                x + 40f, y + 60f,
                nodePaint
            )
            canvas.drawText("Nodo ${leafCounter + 1}", x - 35f, y + 50f, textPaint)

            leafCounter++ // Incrementa il contatore
        } else if (leafCounter == 3) {
            // Disegna il quarto nodo speculare al secondo
            val (originalX, originalY) = firstThreeLeafCoordinates[1] // Secondo nodo
            val mirroredY = originalX - width

            // Disegna il cerchio come nodo speculare
            canvas.drawCircle(originalX, mirroredY, 30f, nodePaint)

            // Disegna il rettangolino con il testo sotto il cerchio speculare
            canvas.drawRect(
                originalX - 40f, mirroredY + 20f,
                originalX + 40f, mirroredY + 60f,
                nodePaint
            )
            canvas.drawText("Nodo ${leafCounter + 1}", originalX - 35f, mirroredY + 50f, textPaint)

            leafCounter++ // Incrementa il contatore
        } else if (leafCounter == 4) {
            // Disegna il quinto nodo speculare alla terza
            val (originalX, originalY) = firstThreeLeafCoordinates[2] // Terzo nodo
            val mirroredY = originalX - width

            // Disegna il cerchio come nodo speculare
            canvas.drawCircle(originalX, mirroredY, 30f, nodePaint)

            // Disegna il rettangolino con il testo sotto il cerchio speculare
            canvas.drawRect(
                originalX - 40f, mirroredY + 20f,
                originalX + 40f, mirroredY + 60f,
                nodePaint
            )
            canvas.drawText("Nodo ${leafCounter + 1}", originalX - 35f, mirroredY + 50f, textPaint)

            leafCounter++ // Incrementa il contatore
        } else {
            // Disegna una foglia come un ovale
            canvas.drawOval(
                x - 15f, // X sinistra
                y - 10f, // Y superiore
                x + 15f, // X destra
                y + 10f, // Y inferiore
                leafPaint
            )
        }
    }


    private fun drawSubBranches(canvas: Canvas, x1: Float, y1: Float, angle: Double, length: Float) {
        val newLength = length * 0.7f // Riduzione della lunghezza per ogni sotto-ramo
        if (newLength < 10) return // Se il sotto-ramo è troppo corto, fermati

        // Disegna il sotto-ramo sinistro
        paint.strokeWidth = subBranchWidth // Imposta spessore del sotto-ramo
        drawBranch(canvas, x1, y1, angle - 30, newLength) // Sotto-ramo sinistro

        // Disegna il sotto-ramo destro
        drawBranch(canvas, x1, y1, angle + 30, newLength) // Sotto-ramo destro
    }


    // Metodo pubblico per incrementare la lunghezza del ramo
    fun incrementBranchLength() {
        // Codice per rendere visibile il primo nodo
        branchLength += incrementValue -5
        //showNextNode()
        shouldDrawBranches = true // Abilita il disegno dei rami
        startBranchAnimation() // Inizia l'animazione del ramo
    }

    private fun startBranchAnimation() {
        val animator = ValueAnimator.ofFloat(0f, branchLength) // Lunghezza attuale del ramo
        animator.duration = 1000 // Durata dell'animazione in millisecondi
        animator.addUpdateListener { animation ->
            branchLength = animation.animatedValue as Float // Aggiorna la lunghezza del ramo
            invalidate() // Richiama il metodo onDraw per ridisegnare la vista
        }
        animator.start() // Avvia l'animazione
    }

    // Gestisci il tocco sullo schermo per il trascinamento
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event) // Gestisci gli eventi di zoom

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                previousX = event.x
                previousY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - previousX
                val dy = event.y - previousY
                translationX += dx
                translationY += dy
                previousX = event.x
                previousY = event.y
                invalidate() // Richiama il metodo onDraw per ridisegnare la vista
            }
        }
        return true // Consenti l'elaborazione dell'evento
    }
}
