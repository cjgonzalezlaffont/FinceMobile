package com.example.fince.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fince.R
import com.example.fince.adapters.StockListAdapter
import com.example.fince.data.model.StockModel
import com.example.fince.databinding.FragmentPanelGeneralBinding
import com.example.fince.databinding.FragmentStocksBinding
import com.example.fince.ui.viewmodel.StockViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController

@AndroidEntryPoint
class PanelGeneralFragment : Fragment(){
    private var _binding: FragmentPanelGeneralBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    lateinit var recStocks: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var stockListAdapter: StockListAdapter
    var stockList: List<StockModel> = ArrayList()
    //se llama a la api por medio del StockViewModel
    private val stockViewModel: StockViewModel by viewModels()
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPanelGeneralBinding.inflate(inflater, container, false)
        view = binding.root

        //Bottom Nav
        bottomNavView = binding.bottomNavigationView
        navHostFragment = childFragmentManager.findFragmentById(binding.fragmentContainerViewPanelGeneral.id) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.stocksFragment,R.id.cedearsFragment, R.id.bondsFragment, R.id.CorporateBondsFragment, R.id.InvestmentFundsFragment))
        bottomNavView.setupWithNavController(navController)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        requireActivity()
        linearLayoutManager = LinearLayoutManager(context)

    }


    /*override fun onViewItemDetail(stock: StockModel) {
        val action = PanelGeneralFragmentDirections.actionPanelGeneralToSimboloFragment(stock.transformStockToActivo(stock))
        this.findNavController().navigate(action)
    }*/

}