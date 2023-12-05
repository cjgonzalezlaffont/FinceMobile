package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.DataEntry
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrincipalViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {
    var ingresosList: MutableList<BarEntryWithLabel> = mutableListOf()
    var egresosList: MutableList<BarEntryWithLabel> = mutableListOf()
    var pieList: MutableList<PieEntry> = mutableListOf()
    var lineListIngresos: MutableList<EntryWithLabel> = mutableListOf()
    var lineListEgresos: MutableList<EntryWithLabel> = mutableListOf()
    var lineListIngresosMediaMovil: MutableList<EntryWithLabel> = mutableListOf()
    var lineListEgresosMediaMovil: MutableList<EntryWithLabel> = mutableListOf()
    var responseToBarChart: MutableList<DataEntry> = mutableListOf()
    var responseToPieChart: MutableList<CategoriaModel> = mutableListOf()
    var responseToLineChart: MutableList<DataEntry> = mutableListOf()

    private val _ingresosListLiveData = MutableLiveData<List<BarEntryWithLabel>>()
    val ingresosListLiveData: LiveData<List<BarEntryWithLabel>> get() = _ingresosListLiveData

    private val _egresosListLiveData = MutableLiveData<List<BarEntryWithLabel>>()
    val egresosListLiveData: LiveData<List<BarEntryWithLabel>> get() = _egresosListLiveData

    private val _pieListLiveData = MutableLiveData<List<PieEntry>>()
    val pieListLiveData: LiveData<List<PieEntry>> get() = _pieListLiveData

    private val _lineListIngresosLiveData = MutableLiveData<List<EntryWithLabel>>()
    val lineListIngresosLiveData: LiveData<List<EntryWithLabel>> get() = _lineListIngresosLiveData

    private val _lineListEgresosLiveData = MutableLiveData<List<EntryWithLabel>>()
    val lineListEgresosLiveData: LiveData<List<EntryWithLabel>> get() = _lineListEgresosLiveData

    private val _lineListMediaMovilEgresosLiveData = MutableLiveData<List<EntryWithLabel>>()
    val lineListMediaMovilEgresosLiveData: LiveData<List<EntryWithLabel>> get() = _lineListMediaMovilEgresosLiveData

    private val _lineListMediaMovilIngresosLiveData = MutableLiveData<List<EntryWithLabel>>()
    val lineListMediaMovilIngresosLiveData: LiveData<List<EntryWithLabel>> get() = _lineListMediaMovilIngresosLiveData

    private fun setResponseAPI(_response: List<DataEntry>) {
        responseToBarChart.clear()
        responseToBarChart.addAll(_response)
    }

    private fun setResponseAPIPieChart(_response: List<CategoriaModel>) {
        responseToPieChart.clear()
        responseToPieChart.addAll(_response)
    }

    private fun setResponseApiLineChart(_response: List<DataEntry>){
        responseToLineChart.clear()
        responseToLineChart.addAll(_response)
    }

    fun onCreate(userId: String) {
        viewModelScope.launch {
            try {
                val responseDataBarChart = repository.getDataGraph(userId)
                if (responseDataBarChart.isNotEmpty()) {
                    //Log.i("RESPONSE",responseDataBarChart.toString())
                    setResponseAPI(responseDataBarChart)
                    updateBarChartLists()
                }

            } catch (e: Exception) {
                Log.e("PrincipalViewModel - BarChart", "Error: ${e.message}")
            }
            try {
                val responseDataPieChart = repository.getAllCategories(userId)
                if (responseDataPieChart.isNotEmpty()) {
                    //Log.i("RESPONSE",responseDataPieChart.toString())
                    setResponseAPIPieChart(responseDataPieChart)
                    updatePieChartList()
                }

            } catch (e: Exception) {
                Log.e("PrincipalViewModel - PieChart", "Error: ${e.message}")
            }
            try {
                val responseDataLineChart = repository.getPrediction(userId)
                if (responseDataLineChart.isNotEmpty()) {
                    //Log.i("RESPONSE",responseDataLineChart.toString())
                    setResponseApiLineChart(responseDataLineChart)
                    updateLineChartList()
                }

            } catch (e: Exception) {
                Log.e("PrincipalViewModel - LineChart", "Error: ${e.message}")
            }

        }
    }

    private fun updatePieChartList() {
        pieList.clear()
        for (category in responseToPieChart) {
            if(category.tipo==0) {
                val pieEntry = PieEntry(category.montoConsumido, category.nombre)
                pieList.add(pieEntry)
            }
        }
        _pieListLiveData.postValue(pieList)
        //Log.i("VIEW MODEL UPDATELISTS: INGRESOS LIST", pieList.toString())
    }

    data class BarEntryWithLabel( @JvmField val x: Float, @JvmField val y: Float, val label: String) : BarEntry(x, y)

    private fun updateBarChartLists() {
        egresosList.clear()
        ingresosList.clear()
        var i = 0.0f
        for (data in responseToBarChart) {
            val monthYearLabel = "${data.month}-${data.year.substring(2)}"

            val barEntryIngresos = BarEntryWithLabel(i, data.ingresos, monthYearLabel)
            val barEntryEgresos = BarEntryWithLabel(i, data.egresos, monthYearLabel)

            ingresosList.add(barEntryIngresos)
            egresosList.add(barEntryEgresos)

            i++
        }

        _ingresosListLiveData.postValue(ingresosList)
        _egresosListLiveData.postValue(egresosList)
    }
    private fun calcularMediaMovil(data: List<DataEntry>, ventanaMediaMovil: Int): Pair<List<Float>, List<Float>> {
        val mediaMovilIngresos = mutableListOf<Float>()
        val mediaMovilEgresos = mutableListOf<Float>()

        for (index in data.indices) {
            if (index < ventanaMediaMovil - 1) {
                mediaMovilIngresos.add(data[index].ingresos)
                mediaMovilEgresos.add(data[index].egresos)
                continue
            }

            val sumIngresos = data.subList(index - ventanaMediaMovil + 1, index + 1)
                .sumOf { it.ingresos.toDouble() }
            val mediaMovilIngreso = sumIngresos / ventanaMediaMovil
            mediaMovilIngresos.add(mediaMovilIngreso.toFloat())

            val sumEgresos = data.subList(index - ventanaMediaMovil + 1, index + 1)
                .sumOf { it.egresos.toDouble() }
            val mediaMovilEgreso = sumEgresos / ventanaMediaMovil
            mediaMovilEgresos.add(mediaMovilEgreso.toFloat())
        }

        return Pair(mediaMovilIngresos, mediaMovilEgresos)
    }

    data class EntryWithLabel(@JvmField val x: Float, @JvmField val y: Float, val label: String) : Entry(x, y)

    private fun updateLineChartList() {
        lineListIngresos.clear()
        lineListEgresos.clear()
        lineListIngresosMediaMovil.clear()
        lineListEgresosMediaMovil.clear()

        val ventanaMediaMovil = 3
        val (mediaMovilIngresos, mediaMovilEgresos) = calcularMediaMovil(responseToLineChart, ventanaMediaMovil)
        var i = 0.0f

        for (entryData in responseToLineChart) {

            val etiquetaGrafico = "${entryData.month}-${entryData.year.substring(2)}"

            val entryIngresos = EntryWithLabel(i, entryData.ingresos, etiquetaGrafico)
            lineListIngresos.add(entryIngresos)

            val entryEgresos = EntryWithLabel(i, entryData.egresos, etiquetaGrafico)
            lineListEgresos.add(entryEgresos)

            val entryMediaMovilIngresos = EntryWithLabel(i, mediaMovilIngresos[i.toInt()], etiquetaGrafico)
            lineListIngresosMediaMovil.add(entryMediaMovilIngresos)

            val entryMediaMovilEgresos = EntryWithLabel(i, mediaMovilEgresos[i.toInt()], etiquetaGrafico)
            lineListEgresosMediaMovil.add(entryMediaMovilEgresos)

            i++
        }

        _lineListIngresosLiveData.postValue(lineListIngresos)
        _lineListEgresosLiveData.postValue(lineListEgresos)
        _lineListMediaMovilIngresosLiveData.postValue(lineListIngresosMediaMovil)
        _lineListMediaMovilEgresosLiveData.postValue(lineListEgresosMediaMovil)
    }

}
