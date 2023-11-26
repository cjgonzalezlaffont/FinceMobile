package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.adapters.ObjetivoAdapter
import com.example.fince.adapters.TransaccionAdapter
import com.example.fince.data.model.ObjetivoModel
import com.example.fince.databinding.FragmentObjetivoBinding
import com.example.fince.listeners.OnEditObjetiveListener
import com.example.fince.listeners.OnViewItemClickedListenerObj
import com.example.fince.ui.viewmodel.ObjetivoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ObjetivoFragment : Fragment(), OnViewItemClickedListenerObj, OnEditObjetiveListener {

    private var _binding: FragmentObjetivoBinding? = null
    private val binding get() = _binding!!
    private lateinit var view : View
    private lateinit var recViewObjetivos : RecyclerView
    var listaObjetivos : MutableList<ObjetivoModel> = ArrayList()
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var objetivoAdapter : ObjetivoAdapter
    private val objetivoViewModel: ObjetivoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentObjetivoBinding.inflate(inflater, container, false)
        view = binding.root

        recViewObjetivos = binding.fragObjRecView

        return view
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")!!

        requireActivity()
        recViewObjetivos.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(context)
        objetivoAdapter = ObjetivoAdapter(listaObjetivos, this)

        recViewObjetivos.layoutManager = linearLayoutManager
        recViewObjetivos.adapter = objetivoAdapter

        objetivoViewModel.onCreate(usuarioId)

        objetivoViewModel.listaObjetivos.observe(viewLifecycleOwner){ response ->
            objetivoAdapter.setObjetivoList(response)
        }

        objetivoViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.fragObjIsLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        binding.fragObjBtnAdd.setOnClickListener {
            val action = ObjetivoFragmentDirections.actionObjetivosToAgregarObjetivoFragment(null)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewItemDetail(objetivo: ObjetivoModel) {
        val dialogFragment = ObjetivoDialogFragment.newInstance(objetivo)
        dialogFragment.setListener(this)
        dialogFragment.show(childFragmentManager, "detalle_dialog")
    }

    override fun onEditObjetive(objetivo: ObjetivoModel) {
        val action = ObjetivoFragmentDirections.actionObjetivosToAgregarObjetivoFragment(objetivo)
        findNavController().navigate(action)
    }
}
