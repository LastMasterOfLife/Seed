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
import com.example.seedapp.databinding.FragmentSeedBinding
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class SeedFragment : Fragment() {

    private lateinit var treeView: TreeView
    lateinit var binding: FragmentSeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_seed, container, false)
       // val spaceContainer: FrameLayout = view.findViewById(R.id.spaceContainer)
       // val addButton: Button = view.findViewById(R.id.addButton)

        treeView = view.findViewById(R.id.treeView)
        val addBranchButton: Button = view.findViewById(R.id.addBranchButton)

        var counttap = 0
        addBranchButton.setOnClickListener {
            if (counttap <7){
                treeView.incrementBranchLength() // Chiamata al metodo per aggiungere un ramo
                counttap++
            }

        }

        return view
    }


}
