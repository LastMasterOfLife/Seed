package com.example.seedapp.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.seedapp.Data.TreeView
import com.example.seedapp.R
import com.example.seedapp.databinding.FragmentSeedBinding

class SeedFragment : Fragment() {

    private lateinit var treeView: TreeView
    private lateinit var binding: FragmentSeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_seed, container, false)

        treeView = view.findViewById(R.id.treeView)
        val addBranchButton: Button = view.findViewById(R.id.addBranchButton)

        var countTap = 0
        addBranchButton.setOnClickListener {
            if (countTap < 7) {
                showCustomDialog { text1, text2 ->
                    // Salva i dati o effettua operazioni
                    Toast.makeText(requireContext(), "Dati salvati: $text1, $text2", Toast.LENGTH_SHORT).show()
                    showTree(countTap)
                    countTap++
                }
            }
        }

        return view
    }

    private fun showCustomDialog(onSave: (String, String) -> Unit) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)
        val editText1: EditText = dialogView.findViewById(R.id.editText1)
        val editText2: EditText = dialogView.findViewById(R.id.editText2)
        val saveButton: Button = dialogView.findViewById(R.id.saveButton)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        saveButton.setOnClickListener {
            val text1 = editText1.text.toString()
            val text2 = editText2.text.toString()

            if (text1.isNotEmpty() && text2.isNotEmpty()) {
                onSave(text1, text2)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Per favore, compila entrambi i campi.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showTree(countTap: Int) {
        if (countTap < 7) {
            treeView.incrementBranchLength(countTap)
        }
    }

    data class DataObject(val text1: String, val text2: String)
}
