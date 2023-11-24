package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.adapters.StockListAdapter
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.StockModel
import com.example.fince.data.network.FinceApiClient
import com.example.fince.databinding.FragmentPanelGeneralBinding
import com.example.fince.listeners.OnViewItemClickedListener
import com.example.fince.ui.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PanelGeneralFragment : Fragment(), OnViewItemClickedListener {
    private var _binding: FragmentPanelGeneralBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    lateinit var recStocks: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var stockListAdapter: StockListAdapter
    var stockList: MutableList<StockModel> = ArrayList()
    //se llama a la api por medio del StockViewModel
    private val stockViewModel: StockViewModel by viewModels()
    private val activoModel = ActivoModel
    private var tipoFiltroActual: String? = null
    private var textoFiltroActual: String = ""
    private var isFirstCreation = true
    private var filterByText : MutableList<StockModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPanelGeneralBinding.inflate(inflater, container, false)
        view = binding.root
        recStocks = binding.panelGeneralRecycleViewStocks

        if (savedInstanceState != null) {
            isFirstCreation = false
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        requireActivity()
        recStocks.setHasFixedSize(true)

        if (isFirstCreation) {
            stockViewModel.onCreate()
            stockListAdapter = StockListAdapter(stockList, this)
            isFirstCreation = false
        } else {
            textoFiltroActual = stockViewModel.filtroTexto
            tipoFiltroActual = stockViewModel.filtroTipo
            stockListAdapter = StockListAdapter(filterByText, this)
        }

        linearLayoutManager = LinearLayoutManager(context)
        recStocks.layoutManager = linearLayoutManager
        recStocks.adapter = stockListAdapter

        binding.fragPanGralFiltAccion.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tipoFiltroActual = "acciones"
            } else {
                tipoFiltroActual = null
            }
            aplicarFiltros()
        }

        binding.fragPanGralFiltCedear.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tipoFiltroActual = "cedears"
            } else {
                tipoFiltroActual = null
            }
            aplicarFiltros()
        }

        binding.fragPanGralFiltTp.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tipoFiltroActual = "titulosPublicos"
            } else {
                tipoFiltroActual = null
            }
            aplicarFiltros()
        }

        binding.fragPanGralFiltFci.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tipoFiltroActual = "FCI"
            } else {
                tipoFiltroActual = null
            }
            aplicarFiltros()
        }

        binding.fragPanGralFiltOn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tipoFiltroActual = "obligacionesNegociables"
            } else {
                tipoFiltroActual = null
            }
            aplicarFiltros()
        }

        binding.fragPanGralFilterSimbolo.addTextChangedListener { textFilter ->
            textoFiltroActual = textFilter.toString()
            aplicarFiltros()
        }

        stockViewModel.response.observe(viewLifecycleOwner){
            stockListAdapter.setStockList(it)
            stockList.clear()
            stockList.addAll(it)
        }

        stockViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.isLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

    }

    private fun aplicarFiltros() {
        filterByText = stockList.filter { stockModel ->
            stockModel.simbolo.contains(textoFiltroActual, ignoreCase = true) &&
                    (tipoFiltroActual == null || stockModel.tipo_instrumento.uppercase() == tipoFiltroActual!!.uppercase())
        }.toMutableList()

        stockViewModel.filtroTexto = textoFiltroActual
        stockViewModel.filtroTipo = tipoFiltroActual

        stockListAdapter.updateStockList(filterByText)
    }

    override fun onViewItemDetail(stock: StockModel) {
        val dialogFragment = SimboloDialogFragment.newInstance(stock.transformStockToActivo(stock))
        dialogFragment.show(childFragmentManager, "detalle_dialog")
    }

    override fun onResume() {
        super.onResume()
        aplicarFiltros()
    }

}