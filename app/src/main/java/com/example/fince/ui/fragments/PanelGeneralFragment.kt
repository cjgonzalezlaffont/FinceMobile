package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.adapters.StockListAdapter
import com.example.fince.data.model.StockModel
import com.example.fince.databinding.FragmentPanelGeneralBinding
import com.example.fince.listeners.OnViewItemClickedListener


class PanelGeneralFragment : Fragment(), OnViewItemClickedListener {
    private var _binding: FragmentPanelGeneralBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    lateinit var recStocks: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var stockListAdapter: StockListAdapter
    var stockList: MutableList<StockModel> ? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

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
        linearLayoutManager = LinearLayoutManager(context)
        stockListAdapter = StockListAdapter(stockList, this)

        recStocks.layoutManager = linearLayoutManager
        recStocks.adapter = stockListAdapter

    }

    override fun onViewItemDetail(stock: StockModel) {
        TODO("Not yet implemented")
    }

}