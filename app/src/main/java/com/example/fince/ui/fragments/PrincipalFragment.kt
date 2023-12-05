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
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
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

    private fun updateBarChart(ingresosList: List<PrincipalViewModel.BarEntryWithLabel>, egresosList: List<PrincipalViewModel.BarEntryWithLabel>) {

        var barData: BarData? = null

        if (ingresosList.isNotEmpty() && egresosList.isNotEmpty()) {

            val ingresosDataSet = BarDataSet(ingresosList, "Ingresos")
            ingresosDataSet.color = ColorTemplate.COLORFUL_COLORS[0]
            ingresosDataSet.setDrawValues(false)
            ingresosDataSet.valueTextSize = 12f

            val egresosDataSet = BarDataSet(egresosList, "Egresos")
            egresosDataSet.color = ColorTemplate.COLORFUL_COLORS[1]
            egresosDataSet.setDrawValues(false)
            egresosDataSet.valueTextSize = 12f

            barData = BarData(ingresosDataSet, egresosDataSet)

        } else {
            Log.e("updateBarChart", "Una o ambas listas estan vacias.")
        }

        if (barData != null) {

            barchart.data = barData
            barchart.xAxis.isEnabled = true
            barchart.axisLeft.isEnabled = true
            barchart.axisRight.isEnabled = false
            barchart.xAxis.labelCount = egresosList.size -1
            barchart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barchart.xAxis.isGranularityEnabled = true
            barchart.xAxis.granularity = 1f
            val groupLabels = egresosList.map { it.label }
            barchart.xAxis.valueFormatter = IndexAxisValueFormatter(groupLabels)
            barchart.animateY(5000)
            barchart.description.text = "Ingresos y Egresos"
            barchart.description.textSize = 16f
            barchart.description.setPosition(800f, 40f)
            barchart.description.textColor = Color.Companion.Black.toArgb()
            barchart.setExtraOffsets(0f, 10f, 0f, 10f)
            barchart.setBackgroundColor(Color.Companion.Gray.toArgb())

        }
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
        pieChart.description.textSize = 16f
        pieChart.description.setPosition(800f, 40f)
        pieChart.description.textColor = Color.Companion.Black.toArgb()
        pieChart.setExtraOffsets(0f, 20f, 0f, 20f)
        pieChart.setBackgroundColor(Color.Companion.Gray.toArgb())
    }

    private fun updateLineChart(
        ingresosLineList: List<PrincipalViewModel.EntryWithLabel>,
        egresosLineList: List<PrincipalViewModel.EntryWithLabel>,
        ingresosMediaMovil: List<PrincipalViewModel.EntryWithLabel>,
        egresosMediaMovil: List<PrincipalViewModel.EntryWithLabel>
    ) {


        Log.i("mediamovilingresos",ingresosMediaMovil.toString())

        val ingresosDataSet = LineDataSet(ingresosLineList, "Ingresos")
        ingresosDataSet.color = ColorTemplate.COLORFUL_COLORS[0]
        ingresosDataSet.setDrawValues(false)
        ingresosDataSet.valueTextSize = 12f

        val egresosDataSet = LineDataSet(egresosLineList, "Egresos")
        egresosDataSet.color = ColorTemplate.COLORFUL_COLORS[1]
        egresosDataSet.setDrawValues(false)
        egresosDataSet.valueTextSize = 12f

        val ingresosMediaMovilDataSet = LineDataSet(ingresosMediaMovil, "Media Móvil Ingresos")
        ingresosMediaMovilDataSet.color = ColorTemplate.COLORFUL_COLORS[2]
        ingresosMediaMovilDataSet.setDrawValues(false)
        ingresosMediaMovilDataSet.valueTextSize = 12f

        val egresosMediaMovilDataSet = LineDataSet(egresosMediaMovil, "Media Móvil Egresos")
        egresosMediaMovilDataSet.color = ColorTemplate.COLORFUL_COLORS[3]
        egresosMediaMovilDataSet.setDrawValues(false)
        egresosMediaMovilDataSet.valueTextSize = 12f

        val lineData = LineData(
            ingresosDataSet,
            egresosDataSet,
            ingresosMediaMovilDataSet,
            egresosMediaMovilDataSet
        )

        lineChart.data = lineData

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = EntryWithLabelXAxisValueFormatter(ingresosLineList)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.labelCount = ingresosLineList.size - 1
        xAxis.spaceMin = 2f
        xAxis.spaceMax = 3f
        xAxis.granularity = 1f

        lineChart.axisLeft.isEnabled = true
        lineChart.axisRight.isEnabled = false
        lineChart.animateY(5000)
        lineChart.description.text = "Pronósticos de Ingresos-Egresos y Medias Móviles"
        lineChart.description.setPosition(1000f, 40f)
        lineChart.description.textSize = 14f
        lineChart.setExtraOffsets(0f, 30f, 0f, 30f)
        lineChart.description.textColor = Color.Companion.Black.toArgb()
        lineChart.setBackgroundColor(Color.Companion.Gray.toArgb())
    }

    class EntryWithLabelXAxisValueFormatter(private val entries: List<PrincipalViewModel.EntryWithLabel>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val index = value.toInt()
            return if (index >= 0 && index < entries.size) {
                entries[index].label
            } else {
                ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
