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
    var ingresosList: MutableList<BarEntry> = mutableListOf()
    var egresosList: MutableList<BarEntry> = mutableListOf()
    var pieList: MutableList<PieEntry> = mutableListOf()
    var lineListIngresos: MutableList<Entry> = mutableListOf()
    var lineListEgresos: MutableList<Entry> = mutableListOf()
    var lineListIngresosMediaMovil: MutableList<Entry> = mutableListOf()
    var lineListEgresosMediaMovil: MutableList<Entry> = mutableListOf()
    var responseToBarChart: MutableList<DataEntry> = mutableListOf()
    var responseToPieChart: MutableList<CategoriaModel> = mutableListOf()
    var responseToLineChart: MutableList<DataEntry> = mutableListOf()

    private val _ingresosListLiveData = MutableLiveData<List<BarEntry>>()
    val ingresosListLiveData: LiveData<List<BarEntry>> get() = _ingresosListLiveData

    private val _egresosListLiveData = MutableLiveData<List<BarEntry>>()
    val egresosListLiveData: LiveData<List<BarEntry>> get() = _egresosListLiveData

    private val _pieListLiveData = MutableLiveData<List<PieEntry>>()
    val pieListLiveData: LiveData<List<PieEntry>> get() = _pieListLiveData

    private val _lineListIngresosLiveData = MutableLiveData<List<Entry>>()
    val lineListIngresosLiveData: LiveData<List<Entry>> get() = _lineListIngresosLiveData

    private val _lineListEgresosLiveData = MutableLiveData<List<Entry>>()
    val lineListEgresosLiveData: LiveData<List<Entry>> get() = _lineListEgresosLiveData

    private val _lineListMediaMovilEgresosLiveData = MutableLiveData<List<Entry>>()
    val lineListMediaMovilEgresosLiveData: LiveData<List<Entry>> get() = _lineListMediaMovilEgresosLiveData

    private val _lineListMediaMovilIngresosLiveData = MutableLiveData<List<Entry>>()
    val lineListMediaMovilIngresosLiveData: LiveData<List<Entry>> get() = _lineListMediaMovilIngresosLiveData

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
                    Log.i("RESPONSE",responseDataPieChart.toString())
                    setResponseAPIPieChart(responseDataPieChart)
                    updatePieChartList()
                }

            } catch (e: Exception) {
                Log.e("PrincipalViewModel - PieChart", "Error: ${e.message}")
            }
            try {
                val responseDataLineChart = repository.getPrediction(userId)
                if (responseDataLineChart.isNotEmpty()) {
                    Log.i("RESPONSE",responseDataLineChart.toString())
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
        Log.i("VIEW MODEL UPDATELISTS: INGRESOS LIST", pieList.toString())
    }

    private fun updateBarChartLists() {
        egresosList.clear()
        ingresosList.clear()
        var i = 1.0f
        for (data in responseToBarChart) {
            val barEntryIngresos = BarEntry(i, data.ingresos)
            ingresosList.add(barEntryIngresos)
            val barEntryEgresos = BarEntry(i, data.egresos)
            egresosList.add(barEntryEgresos)
            i++
        }

        _ingresosListLiveData.postValue(ingresosList)
        _egresosListLiveData.postValue(egresosList)

        //Log.i("VIEW MODEL UPDATELISTS: INGRESOS LIST", ingresosList.toString())
        //Log.i("VIEW MODEL UPDATELISTS: EGRESOS LIST", egresosList.toString())
    }
    private fun calcularMediaMovil(data: List<DataEntry>, ventanaMediaMovil: Int): Pair<List<Float>, List<Float>> {
        val mediaMovilIngresos = mutableListOf<Float>()
        val mediaMovilEgresos = mutableListOf<Float>()

        for (index in data.indices) {
            if (index < ventanaMediaMovil - 1) {
                mediaMovilIngresos.add(0f)
                mediaMovilEgresos.add(0f)
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
    private fun updateLineChartList() {
        lineListIngresos.clear()
        lineListEgresos.clear()
        lineListIngresosMediaMovil.clear()
        lineListEgresosMediaMovil.clear()

        val ventanaMediaMovil = 3  // refierea cuantos puntos toma para la media, +ptos + suave la curva
        val (mediaMovilIngresos, mediaMovilEgresos) = calcularMediaMovil(responseToLineChart, ventanaMediaMovil)
        var i = 1.0f

        for (index in responseToLineChart.indices) {

            val entryIngresos = Entry(i, responseToLineChart[index].ingresos)
            lineListIngresos.add(entryIngresos)

            val entryEgresos = Entry(i, responseToLineChart[index].egresos)
            lineListEgresos.add(entryEgresos)

            val entryMediaMovilIngresos = Entry(i, mediaMovilIngresos[index])
            lineListIngresosMediaMovil.add(entryMediaMovilIngresos)

            val entryMediaMovilEgresos = Entry(i, mediaMovilEgresos[index])
            lineListEgresosMediaMovil.add(entryMediaMovilEgresos)

            i++
        }

        _lineListIngresosLiveData.postValue(lineListIngresos)
        _lineListEgresosLiveData.postValue(lineListEgresos)
        _lineListMediaMovilIngresosLiveData.postValue(lineListIngresosMediaMovil)
        _lineListMediaMovilEgresosLiveData.postValue(lineListEgresosMediaMovil)
    }

}
