package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fince.databinding.FragmentPrincipalBinding
import com.example.fince.ui.viewmodel.PrincipalViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrincipalFragment : Fragment() {
    private var _binding: FragmentPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private lateinit var barchart: BarChart
    private lateinit var pieChart: PieChart
    private val viewModel: PrincipalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrincipalBinding.inflate(inflater, container, false)
        view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireContext().getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE)
        val usuarioId = sharedPreferences.getString("userId", "")
        usuarioId?.let {
            viewModel.onCreate(it)
        } ?: run {
            Toast.makeText(requireContext(), "UsuarioId nulo", Toast.LENGTH_SHORT).show()
        }
        setupCharts()
        setupViewModelObservers()
    }

    private fun setupCharts() {
        barchart = binding.barChart
        pieChart = binding.pieChart
    }

    private fun setupViewModelObservers() {
        viewModel.ingresosListLiveData.observe(viewLifecycleOwner) { _ ->
            updateBarChart(viewModel.ingresosList.toList(), viewModel.egresosList.toList())
        }
        viewModel.egresosListLiveData.observe(viewLifecycleOwner) { _ ->
            updateBarChart(viewModel.ingresosList.toList(), viewModel.egresosList.toList())
        }
        viewModel.pieEntriesLiveData.observe(viewLifecycleOwner) { pieEntries ->
            updatePieChart(pieEntries)
        }
    }

    private fun updateBarChart(ingresosList: List<BarEntry>, egresosList: List<BarEntry>) {

        //Log.i("updateBarChart:ingresosList",ingresosList.toString())
        //Log.i("updateBarChart:egresosList",ingresosList.toString())

        val ingresosDataSet = BarDataSet(ingresosList, "Ingresos")
        ingresosDataSet.color = ColorTemplate.COLORFUL_COLORS[0]
        ingresosDataSet.setDrawValues(true)

        val egresosDataSet = BarDataSet(egresosList, "Egresos")
        egresosDataSet.color = ColorTemplate.COLORFUL_COLORS[1]
        egresosDataSet.setDrawValues(true)

        val barData = BarData(ingresosDataSet, egresosDataSet)

        barchart.data = barData
        barchart.groupBars(0f, 0.5f, 0.1f)
        barchart.animateY(5000)
        barchart.description.text = "Descripción para ingresos y egresos"
        barchart.description.textColor = Color.Companion.White.toArgb()
    }

    private fun updatePieChart(pieEntries: List<PieEntry>) {
        val pieDataSet = PieDataSet(pieEntries, "Gastos por Categorías")
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        pieDataSet.setDrawValues(true)

        val pieData = PieData(pieDataSet)

        pieChart.data = pieData
        pieChart.animateXY(5000, 5000)
        pieChart.description.text = "Descripción para gastos"
        pieChart.description.textColor = Color.Companion.White.toArgb()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
