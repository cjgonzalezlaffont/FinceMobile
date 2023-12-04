package com.example.fince.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrincipalFragment : Fragment() {
    private var _binding: FragmentPrincipalBinding? = null
    private val binding get() = _binding!!
    private lateinit var view: View
    private lateinit var barchart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var lineChart: LineChart
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
        lineChart = binding.LineChart
    }

    private fun setupViewModelObservers() {
        viewModel.ingresosListLiveData.observe(viewLifecycleOwner) { _ ->
            updateBarChart(viewModel.ingresosList.toList(), viewModel.egresosList.toList())
        }
        viewModel.egresosListLiveData.observe(viewLifecycleOwner) { _ ->
            updateBarChart(viewModel.ingresosList.toList(), viewModel.egresosList.toList())
        }
        viewModel.pieListLiveData.observe(viewLifecycleOwner) { _ ->
            updatePieChart(viewModel.pieList.toList())
        }
        viewModel.lineListIngresosLiveData.observe(viewLifecycleOwner) { _ ->
            updateLineChart(viewModel.lineListIngresos.toList(), viewModel.lineListEgresos.toList(),viewModel.lineListIngresosMediaMovil.toList(),viewModel.lineListEgresosMediaMovil.toList())
        }
        viewModel.lineListEgresosLiveData.observe(viewLifecycleOwner) { _ ->
            updateLineChart(viewModel.lineListIngresos.toList(), viewModel.lineListEgresos.toList(),viewModel.lineListIngresosMediaMovil.toList(),viewModel.lineListEgresosMediaMovil.toList())
        }
        viewModel.lineListMediaMovilIngresosLiveData.observe(viewLifecycleOwner) { _ ->
            updateLineChart(viewModel.lineListIngresos.toList(), viewModel.lineListEgresos.toList(),viewModel.lineListIngresosMediaMovil.toList(),viewModel.lineListEgresosMediaMovil.toList())
        }
        viewModel.lineListMediaMovilEgresosLiveData.observe(viewLifecycleOwner) { _ ->
            updateLineChart(viewModel.lineListIngresos.toList(), viewModel.lineListEgresos.toList(),viewModel.lineListIngresosMediaMovil.toList(),viewModel.lineListEgresosMediaMovil.toList())
        }

    }

    private fun updateBarChart(ingresosList: List<BarEntry>, egresosList: List<BarEntry>) {

        //Log.i("updateBarChart:ingresosList",ingresosList.toString())
        //Log.i("updateBarChart:egresosList",ingresosList.toString())

        val ingresosDataSet = BarDataSet(ingresosList, "Ingresos")
        ingresosDataSet.color = ColorTemplate.COLORFUL_COLORS[0]
        ingresosDataSet.setDrawValues(true)
        ingresosDataSet.valueTextSize = 12f
        val egresosDataSet = BarDataSet(egresosList, "Egresos")
        egresosDataSet.color = ColorTemplate.COLORFUL_COLORS[1]
        egresosDataSet.setDrawValues(true)
        egresosDataSet.valueTextSize = 12f
        val barData = BarData(ingresosDataSet, egresosDataSet)

        barchart.data = barData
        barchart.xAxis.isEnabled = false
        barchart.axisLeft.isEnabled = false
        barchart.axisRight.isEnabled = false


        barchart.groupBars(0f, 0.9f, 0.9f)
        barchart.animateY(5000)
        barchart.description.text = "Ingresos y Egresos"
        barchart.description.textColor = Color.Companion.Black.toArgb()
        barchart.setBackgroundColor(Color.Companion.Gray.toArgb())


    }

    private fun updatePieChart(pieEntries: List<PieEntry>) {
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        pieDataSet.setDrawValues(true)
        pieDataSet.valueTextSize = 14f
        val pieData = PieData(pieDataSet)

        pieChart.data = pieData
        pieChart.animateXY(5000, 5000)
        pieChart.description.text = "Gastos por Categorías"
        pieChart.description.textColor = Color.Companion.Black.toArgb()
        pieChart.setBackgroundColor(Color.Companion.Gray.toArgb())
    }

    private fun updateLineChart(
        ingresosLineList: List<Entry>,
        egresosLineList: List<Entry>,
        ingresosMediaMovil: List<Entry>,
        egresosMediaMovil: List<Entry>
    ) {

        Log.i("updateLineChart:ingresosList",ingresosLineList.toString())
        Log.i("updateLineChart:egresosList",egresosLineList.toString())
        Log.i("updateLineChart:egresosMediaMovil",egresosMediaMovil.toString())
        Log.i("updateLineChart:ingresosMediaMovil",ingresosMediaMovil.toString())


        val ingresosDataSet = LineDataSet(ingresosLineList, "Ingresos")
        ingresosDataSet.color = ColorTemplate.COLORFUL_COLORS[0]
        ingresosDataSet.setDrawValues(true)
        ingresosDataSet.valueTextSize = 12f

        val egresosDataSet = LineDataSet(egresosLineList, "Egresos")
        egresosDataSet.color = ColorTemplate.COLORFUL_COLORS[1]
        egresosDataSet.setDrawValues(true)
        egresosDataSet.valueTextSize = 12f

        val ingresosMediaMovilDataSet = LineDataSet(ingresosMediaMovil, "Media Móvil Ingresos")
        ingresosMediaMovilDataSet.color = ColorTemplate.COLORFUL_COLORS[2]
        ingresosMediaMovilDataSet.setDrawValues(true)
        ingresosMediaMovilDataSet.valueTextSize = 12f

        val egresosMediaMovilDataSet = LineDataSet(egresosMediaMovil, "Media Móvil Egresos")
        egresosMediaMovilDataSet.color = ColorTemplate.COLORFUL_COLORS[3]
        egresosMediaMovilDataSet.setDrawValues(true)
        egresosMediaMovilDataSet.valueTextSize = 12f

        val lineData = LineData(
            ingresosDataSet,
            egresosDataSet,
            ingresosMediaMovilDataSet,
            egresosMediaMovilDataSet
        )

        lineChart.data = lineData

        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true

        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        lineChart.xAxis.labelCount = 12

        lineChart.axisLeft.isEnabled = false
        lineChart.axisRight.isEnabled = false

        lineChart.animateY(5000)

        lineChart.description.text = "Pronósticos de Ingresos-Egresos y Medias Móviles"
        lineChart.description.textColor = Color.Companion.Black.toArgb()
        lineChart.setBackgroundColor(Color.Companion.Gray.toArgb())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
