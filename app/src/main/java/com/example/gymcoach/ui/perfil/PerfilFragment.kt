package com.example.gymcoach.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.gymcoach.databinding.FragmentPerfilBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PerfilFragment : Fragment() {

private var _binding: FragmentPerfilBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    private val uploadViewModel: PerfilViewModel by viewModels()
    private var intentgallerylauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the returned URI
        uri?.let {
            uploadViewModel.uploadBasicImage(it)
        }
    }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _binding = FragmentPerfilBinding.inflate(inflater, container, false)
    return binding.root
  }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabImage.setOnClickListener {
            intentgallerylauncher.launch("image/*")
        }
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}