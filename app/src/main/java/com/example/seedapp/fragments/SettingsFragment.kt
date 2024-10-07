package com.example.seedapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import com.example.seedapp.NotificationFragment
import com.example.seedapp.R
import com.example.seedapp.databinding.FragmentSettingsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Utilizza il binding per impostare l'azione del pulsante
        binding.notifiche.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, NotificationFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.suono.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.container, SoundFragment()).addToBackStack(null).commit()
        }

        binding.lingua.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, LanguageFragment()).addToBackStack(null).commit()
        }

        binding.privacy.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, PrivacyFragment()).addToBackStack(null).commit()
        }

        binding.policy.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, PolicyFragment()).addToBackStack(null).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}