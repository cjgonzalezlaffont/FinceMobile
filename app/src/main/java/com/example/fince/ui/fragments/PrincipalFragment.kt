package com.example.fince.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import com.example.fince.databinding.FragmentPrincipalBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class PrincipalFragment : Fragment() {
    private var _binding: FragmentPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private lateinit var barchart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var barEntries: ArrayList<BarEntry>
    private lateinit var pieEntries: ArrayList<PieEntry>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrincipalBinding.inflate(inflater, container, false)
        view = binding.root
        barchart = binding.barChart
        pieChart = binding.pieChart
        barEntries = ArrayList()
        pieEntries = ArrayList()

        //mock de datos -> ACA API FINCE

        for (i in 0 until 10) {
            val valor = i * 10f
            barEntries.add(BarEntry(i.toFloat(), valor))
            pieEntries.add(PieEntry(i.toFloat(), valor))
        }

        // CONF DE GRAFICOS

        val barDataSet = BarDataSet(barEntries, "Empleados")
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        barDataSet.setDrawValues(true)

        barchart.data = BarData(barDataSet)
        barchart.animateY(5000)
        barchart.description.text = "A VER SI ANDA"
        barchart.description.textColor = Color.Companion.Blue.toArgb()

        val pieDataSet = PieDataSet(pieEntries,"Gastos")
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        pieDataSet.setDrawValues(true)
        pieChart.data = PieData(pieDataSet)
        pieChart.animateXY(5000,5000)
        pieChart.description.text = "A VER SI ANDA 2"
        pieChart.description.textColor = Color.Companion.Blue.toArgb()

        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}
