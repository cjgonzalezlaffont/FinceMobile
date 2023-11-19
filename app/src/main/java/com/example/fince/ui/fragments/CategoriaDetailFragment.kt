package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.example.fince.data.model.CategoriaModel
import com.example.fince.databinding.FragmentCategoriaDetailBinding
import com.example.fince.ui.viewmodel.CategoriaViewModel

class CategoriaDetailFragment : Fragment() {


    private var _binding: FragmentCategoriaDetailBinding? = null
    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private lateinit var view: View
    private val binding get() = _binding!!
    private val categoriaList = MutableLiveData<List<CategoriaModel>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriaDetailBinding.inflate(inflater, container, false)
        view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences =
            requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        //recibo activo por arguments
        val args: CategoriaDetailFragmentArgs by navArgs()
        val categoria: CategoriaModel = args.categoria


        //se lo paso a la vista
        binding.FragCatDetailTxtViewNombre.text = categoria.nombre


        //seteo los listener de la vista son 6


    }
}