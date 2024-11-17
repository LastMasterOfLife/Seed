package com.example.seedapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seedapp.Data.TreeView
import com.example.seedapp.R
import com.example.seedapp.databinding.FragmentGoalsBinding

class GoalsFragment : Fragment() {

    private var _binding: FragmentGoalsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGoalsBinding.inflate(inflater, container, false)

        val add = binding.addBranchButton
        add.setOnClickListener {
           val treeView = (parentFragmentManager.findFragmentById(R.id.main) as? SeedFragment)?.binding?.treeView?.incrementBranchLength()
        }

        return binding.root
    }


}