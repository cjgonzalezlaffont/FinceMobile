package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.adapters.CategoriaAdapter
import com.example.fince.adapters.TransaccionAdapter
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.Transaccion
import com.example.fince.databinding.FragmentCategoriasBinding
import com.example.fince.databinding.FragmentPresupuestoBinding
import com.example.fince.listeners.OnViewItemClickedListenerCat
import com.example.fince.ui.viewmodel.CategoriaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriaFragment : Fragment(), OnViewItemClickedListenerCat {

    lateinit var v : View
    lateinit var recCategoria : RecyclerView
    var listaCategorias : MutableList<CategoriaModel> = ArrayList()
    private lateinit var  linearLayoutManager: LinearLayoutManager
    private lateinit var categoriaAdapter: CategoriaAdapter
    private var _binding: FragmentCategoriasBinding? = null
    private val binding get() = _binding!!
    private  val categoriaViewModel: CategoriaViewModel by viewModels()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriasBinding.inflate(inflater, container, false)
        v = binding.root
        recCategoria = binding.fragCatRecViewCategoria

        return v
    }
    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        categoriaViewModel.onCreate(usuarioId)

        requireActivity()
        recCategoria.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)
        categoriaAdapter = CategoriaAdapter(listaCategorias, this)

        recCategoria.layoutManager = linearLayoutManager
        recCategoria.adapter = categoriaAdapter

        categoriaViewModel.categoriaList.observe(viewLifecycleOwner) {
            categoriaAdapter.setCategoriaList(it)
        }

        categoriaViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.isLoadingFragCat.visibility = if (it) View.VISIBLE else View.GONE
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onViewItemDetail(categoria: CategoriaModel) {
        val dialogFragment = CategoriaDialogFragment.newInstance(categoria)
        dialogFragment.show(childFragmentManager, "detalle_dialog")

        }

    }
