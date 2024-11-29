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
import android.content.Intent
import android.graphics.PointF
import android.graphics.RectF
import android.graphics.Typeface
import android.util.Log
import android.widget.Toast
import com.example.seedapp.DatailGoalsActivity
import com.example.seedapp.GeneraliActivity
import kotlin.math.cos
import kotlin.math.sin

class TreeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var branchCounter = 0 // Contatore per ID univoci dei rami
    private var branchnumber =0
    private val branchMap = mutableMapOf<Int, Branch>() // Mappa per i rami

    private var leafcount = 0
    private val leafPositions = mutableListOf<PointF>()  // Supponiamo che tu abbia una lista di posizioni delle foglie


    var tocchi = 0

    data class Branch(
        val id: Int = 0,
        val startX: Float,
        val startY: Float,
        val endX: Float,
        val endY: Float,
        var leafCounter: Int = 0 // Contatore foglie univoco per ogni ramo
    )

    private fun resetTree() {
        branchMap.clear()
        branchCounter = 0
        //branchLength = 0f
        shouldDrawBranches = false
        invalidate()
    }

    private val Trunkpaint = Paint().apply {
        color = Color.rgb(101, 67, 33) // Marrone per tronco e rami
        //color = Color.TRANSPARENT
        isAntiAlias = true
    }

    private val paint = Paint().apply {
        color = Color.rgb(101, 67, 33) // Marrone per tronco e rami
        //color = Color.TRANSPARENT
        isAntiAlias = true
    }

    private var branchLength = 0f // Lunghezza attuale del ramo
    private val incrementValue = 70f // Valore da incrementare ad ogni clic sul pulsante
    private var shouldDrawBranches = false // Variabile per controllare il disegno dei rami

    // Variabili per lo zoom e trascinamento
    private var previousX = 0f
    private var previousY = 0f
    private var translationX = 0f
    private var translationY = 0f
    private var scaleFactor = 1f
    private val scaleGestureDetector: ScaleGestureDetector

    // Spessori per tronco, rami e sotto-rami
    private val trunkWidth = 40f
    private val branchWidth = 25f
    private val subBranchWidth = 8f

    private val leafPaint = Paint().apply {
        color = 0xFF4CAF50.toInt() // Colore verde per le foglie
        style = Paint.Style.FILL
    }

    private fun addLeafPosition(x: Float, y: Float) {
        leafPositions.add(PointF(x, y))
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 20f
        isAntiAlias = true
    }

    init {
        scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(0.1f, 5.0f)
                invalidate()
                return true
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()

        leafcount = 0
        leafPositions.clear()

        // Applica trasformazioni di zoom e traslazione
        canvas.translate(translationX, translationY)
        canvas.scale(scaleFactor, scaleFactor)

        val startX = width / 2f
        val startY = height.toFloat()
        val trunkHeight = 100f
        val bottomWidth = 100f // Larghezza alla base del tronco
        val topWidth = 25f // Larghezza alla cima del tronco
        val trunkSegments = 100 // Numero di segmenti per creare l'effetto gradiente

        // Disegna il tronco a segmenti, diminuendo la larghezza verso l'alto
        for (i in 0 until trunkSegments) {
            // Calcola la posizione verticale per ogni segmento
            val segmentStartY = startY - (i * (trunkHeight / trunkSegments))
            val segmentEndY = startY - ((i + 1) * (trunkHeight / trunkSegments))

            // Calcola la larghezza per ogni segmento
            Trunkpaint.strokeWidth = bottomWidth - i * ((bottomWidth - topWidth) / trunkSegments)

            // Disegna il segmento
            canvas.drawLine(startX, segmentStartY, startX, segmentEndY, Trunkpaint)
        }

        // Posizione del primo ramo subito sopra il tronco
        val branchStartX = startX
        val branchStartY = startY - trunkHeight // Subito sopra il tronco

        // Disegna il primo ramo
        if (shouldDrawBranches) {
            drawBranch(canvas, branchStartX, branchStartY, -90.0, branchLength) // Primo ramo
        }

        // Disegna il cerchio e il rettangolo sulla prima foglia

        aiuto(canvas,tocchi)
        Log.d("foglieTot", "Numero totale foglie: ${leafPositions.size}")
        canvas.restore()
    }

    private fun aiuto( canvas: Canvas,num: Int){

        if (leafPositions.size > 1) {
            if (num == 0) {
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[0], "1")
            }
            if (num == 1) {
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[0], "1")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[1], "2")
            }


            if (num == 2){
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[0], "1")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[1], "2")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[256], "3")
            }

            if (num == 3){
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[0], "1")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[1], "2")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[256], "3")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[2], "4")
            }

            if (num == 4){
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[0], "1")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[1], "2")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[256], "3")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[2], "4")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[768], "5")
            }
            if (num == 5){
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[0], "1")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[1], "2")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[256], "3")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[2], "4")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[768], "5")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[514], "6")
            }
            if (num == 6){
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[0], "1")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[1], "2")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[256], "3")
                drawCircleAndRectangleOnLeaf(canvas, leafPositions[2], "4")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[768], "5")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[514], "6")
                //drawCircleAndRectangleOnLeaf(canvas, leafPositions[1026], "7")
            }


        }
    }

    private fun isPointInsideCircle(x: Float, y: Float, circleX: Float, circleY: Float, radius: Float): Boolean {
        val distance = Math.sqrt(((x - circleX) * (x - circleX) + (y - circleY) * (y - circleY)).toDouble())
        return distance <= radius
    }
    private fun drawCircleAndRectangleOnLeaf(canvas: Canvas, leafPosition: PointF, text: String) {
        // Disegna il cerchio con bordino
        val outerCirclePaint = Paint().apply {
            color = Color.rgb(49, 156, 115) // quaternario
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val innerCirclePaint = Paint().apply {
            color = Color.GREEN // Colore del cerchio interno
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val radius = 30f
        canvas.drawCircle(leafPosition.x, leafPosition.y, radius + 5f, outerCirclePaint) // Bordino
        canvas.drawCircle(leafPosition.x, leafPosition.y, radius, innerCirclePaint) // Cerchio interno

        // Disegna il rettangolo con bordi arrotondati
        val rectWidth = 80f
        val rectHeight = 45f
        val rectPaint = Paint().apply {
            color = Color.rgb(49, 156, 115) // quaternario
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val rectLeft = leafPosition.x - rectWidth / 2
        val rectTop = leafPosition.y + radius + 10
        val rectRight = leafPosition.x + rectWidth / 2
        val rectBottom = rectTop + rectHeight
        val rect = RectF(rectLeft, rectTop, rectRight, rectBottom)
        canvas.drawRoundRect(rect, 15f, 15f, rectPaint) // Angoli arrotondati

        // Disegna il testo in grassetto
        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 20f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD) // Grassetto
        }
        canvas.drawText(text, leafPosition.x, rectTop + rectHeight / 2 + 8, textPaint)
    }



    private fun drawBranch(canvas: Canvas, x1: Float, y1: Float, angle: Double, length: Float) {
        if (length < 10) return // Stop se il ramo è troppo corto

        // Calcola le coordinate del punto finale del ramo
        val x2 = x1 + (length * cos(Math.toRadians(angle))).toFloat()
        val y2 = y1 + (length * sin(Math.toRadians(angle))).toFloat()

        // Assegna un ID univoco al ramo e crea un nuovo ramo
        val branchId = branchCounter++
        val branch = Branch(branchId, x1, y1, x2, y2)
        branchMap[branchId] = branch


        //branchnumber =+ branch.id-(branch.id/2)-((branch.id/2)-branchnumber++)
        branchnumber = leafcount

        //branchCounter = branchId

        // Disegna il ramo
        paint.strokeWidth = branchWidth
        canvas.drawLine(x1, y1, x2, y2, paint)

        // Disegna foglie per il ramo corrente
        drawLeaves(canvas, x2, y2, branch)

        // Disegna sotto-rami
        drawSubBranches(canvas, x2, y2, angle, length, branch)
    }

    private fun drawLeaves(canvas: Canvas, x: Float, y: Float, branch: Branch) {
        //branch.leafCounter++ // Incrementa il contatore delle foglie per il ramo corrente
        leafcount++

        // Disegna una foglia come un ovale
        canvas.drawOval(
            x - 15f, y - 10f,
            x + 15f, y + 10f,
            leafPaint
        )
        addLeafPosition(x, y)

        /*

        // Disegna il numero univoco della foglia accanto ad essa
        canvas.drawText(
            "n° ${leafcount}",
            x + 20f, y, textPaint
        )

         */


        Log.d("TreeView", "Leaf counter: $leafcount, Leaf positions: ${leafPositions.size}")


    }

    private fun drawSubBranches(canvas: Canvas, x1: Float, y1: Float, angle: Double, length: Float, parentBranch: Branch) {
        val newLength = length * 0.7f
        if (newLength < 10) return

        // Sotto-ramo sinistro
        paint.strokeWidth = subBranchWidth
        drawBranch(canvas, x1, y1, angle - 30, newLength)

        // Sotto-ramo destro
        drawBranch(canvas, x1, y1, angle + 30, newLength)
    }

    // Metodo pubblico per incrementare la lunghezza del ramo
    fun incrementBranchLength(tap: Int) {
        resetTree()
        branchLength += incrementValue
        tocchi = tap
        shouldDrawBranches = true
        startBranchAnimation()
    }

    private fun startBranchAnimation() {
        val animator = ValueAnimator.ofFloat(0f, branchLength)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            branchLength = animation.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    // Gestisci il tocco sullo schermo per zoom e trascinamento
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                previousX = event.x
                previousY = event.y

                // Verifica se il tocco è all'interno di un cerchio
                for (leaf in leafPositions) {
                    if (isPointInsideCircle(event.x, event.y, leaf.x, leaf.y, 30f)) {
                        // Esegui l'azione quando il cerchio viene cliccato
                        Toast.makeText(context, "è stato premuto un obbiettivo", Toast.LENGTH_SHORT)
                            .show()

                        val intent = Intent(context, DatailGoalsActivity::class.java)
                        intent.putExtra("numero","2")
                        context.startActivity(intent)

                        Log.d("TreeView", "Cerchio cliccato a posizione: ${leaf.x}, ${leaf.y}")
                        // Puoi aggiungere qui l'azione che desideri, ad esempio cambiare il colore del cerchio
                        return true // Per evitare che l'evento venga elaborato ulteriormente
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - previousX
                val dy = event.y - previousY
                translationX += dx
                translationY += dy
                previousX = event.x
                previousY = event.y
                invalidate()
            }
        }
        return true
    }
}
