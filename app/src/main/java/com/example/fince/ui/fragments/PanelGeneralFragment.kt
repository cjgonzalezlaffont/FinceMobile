package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.adapters.StockListAdapter
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
    var stockList: List<StockModel> = ArrayList()
    //se llama a la api por medio del StockViewModel
    private val stockViewModel: StockViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPanelGeneralBinding.inflate(inflater, container, false)
        view = binding.root
        recStocks = binding.panelGeneralRecycleViewStocks
        return view
    }

    override fun onStart() {
        super.onStart()
        requireActivity()
        recStocks.setHasFixedSize(true)

        stockViewModel.onCreate()
        linearLayoutManager = LinearLayoutManager(context)

        stockViewModel.response.observe(viewLifecycleOwner){
            stockListAdapter.setStockList(it)
        }

        stockListAdapter = StockListAdapter(stockList, this)

        recStocks.layoutManager = linearLayoutManager
        recStocks.adapter = stockListAdapter

    }

    override fun onViewItemDetail(stock: StockModel) {
        TODO("Not yet implemented")
    }

}