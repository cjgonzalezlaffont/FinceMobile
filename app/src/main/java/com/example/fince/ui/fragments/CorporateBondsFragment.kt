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
import com.example.fince.data.model.StockModel
import com.example.fince.databinding.FragmentCedearsBinding
import com.example.fince.databinding.FragmentCorporateBondsBinding
import com.example.fince.listeners.OnViewItemClickedListener
import com.example.fince.ui.viewmodel.StockViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class CorporateBondsFragment : Fragment(), OnViewItemClickedListener {
    private var _binding: FragmentCorporateBondsBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    lateinit var recCorporateBonds: RecyclerView
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
        _binding = FragmentCorporateBondsBinding.inflate(inflater, container, false)
        view = binding.root
        recCorporateBonds = binding.panelGeneralRecycleViewCorporateBonds
        return view
    }
    override fun onStart() {
        super.onStart()
        requireActivity()
        recCorporateBonds.setHasFixedSize(true)

        stockViewModel.onCreateCorporateBonds()
        linearLayoutManager = LinearLayoutManager(context)
        stockViewModel.response.observe(viewLifecycleOwner){
            stockListAdapter.setStockList(it)
        }
        stockListAdapter = StockListAdapter(stockList, this)
        recCorporateBonds.layoutManager = linearLayoutManager
        recCorporateBonds.adapter = stockListAdapter

        stockViewModel.isLoading.observe(viewLifecycleOwner) {
            //binding.isLoading.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    override fun onViewItemDetail(stock: StockModel) {
        val action = CorporateBondsFragmentDirections.actionCorporateBondsFragmentToSimboloFragment2(stock.transformStockToActivo(stock))
        this.findNavController().navigate(action)
    }

}