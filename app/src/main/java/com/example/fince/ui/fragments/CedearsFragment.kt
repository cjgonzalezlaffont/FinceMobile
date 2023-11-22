package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.adapters.StockListAdapter
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.StockModel
import com.example.fince.databinding.FragmentCedearsBinding
import com.example.fince.databinding.FragmentPanelGeneralBinding
import com.example.fince.listeners.OnViewItemClickedListener
import com.example.fince.ui.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.ViewModelLifecycle
import java.util.ArrayList

@AndroidEntryPoint
class CedearsFragment : Fragment(), OnViewItemClickedListener {
    private var _binding: FragmentCedearsBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    lateinit var recCedears: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var stockListAdapter: StockListAdapter
    var stockList: List<StockModel> = ArrayList()
    //se llama a la api por medio del StockViewModel
    private val stockViewModel: StockViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCedearsBinding.inflate(inflater, container, false)
        view = binding.root
        recCedears = binding.panelGeneralRecycleViewCedears
        return view
    }

    override fun onStart() {
        super.onStart()
        requireActivity()
        recCedears.setHasFixedSize(true)

        stockViewModel.onCreateCedears()
        linearLayoutManager = LinearLayoutManager(context)
        stockViewModel.response.observe(viewLifecycleOwner){
            stockListAdapter.setStockList(it)
        }
        stockListAdapter = StockListAdapter(stockList, this)
        recCedears.layoutManager = linearLayoutManager
        recCedears.adapter = stockListAdapter

        stockViewModel.isLoading.observe(viewLifecycleOwner) {
            //binding.isLoading.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
    override fun onViewItemDetail(stock: StockModel) {
        val action = CedearsFragmentDirections.actionCedearsFragmentToSimboloFragment(stock.transformStockToActivo(stock))
        this.findNavController().navigate(action)
    }
}