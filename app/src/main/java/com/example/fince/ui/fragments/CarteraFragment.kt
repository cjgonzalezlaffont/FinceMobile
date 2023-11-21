package com.example.fince.ui.fragments
import CarteraListAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.data.model.ActivoModel
import com.example.fince.databinding.FragmentCarteraBinding
import com.example.fince.listeners.OnViewItemClickedListenerCartera
import com.example.fince.ui.viewmodel.CarteraViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CarteraFragment : Fragment(), OnViewItemClickedListenerCartera {
    private var _binding: FragmentCarteraBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private lateinit var recyclerViewCartera: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var carteraListAdapter: CarteraListAdapter
    private var carteraList: List<ActivoModel> = ArrayList()
    private val carteraViewModel: CarteraViewModel by viewModels()
    private var usuarioId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarteraBinding.inflate(inflater, container, false)
        view = binding.root
        recyclerViewCartera = binding.CarteraRecycleView
        return view
    }

    override fun onStart() {
        super.onStart()
        requireActivity()
        recyclerViewCartera.setHasFixedSize(true)
        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        usuarioId = sharedPreferences.getString("userId", "")!!
        carteraViewModel.onCreate(usuarioId)
        linearLayoutManager = LinearLayoutManager(context)
        carteraListAdapter = CarteraListAdapter(carteraList, this)
        recyclerViewCartera.layoutManager = linearLayoutManager
        recyclerViewCartera.adapter = carteraListAdapter

        carteraViewModel.carteraList.observe(viewLifecycleOwner) {
            carteraListAdapter.setCarteraList(it)
        }

    }
        override fun onViewItemDetail(activo : ActivoModel){
            val action = CarteraFragmentDirections.actionCarteraToSimboloFragment(activo) //activo
            this.findNavController().navigate(action)
        }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            try {
                val updatedList = carteraViewModel.getUpdatedCarteraList(usuarioId)
                carteraViewModel.setTransaccionList(updatedList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    }


