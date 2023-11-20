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
    var responseToBarChart: MutableList<DataEntry> = mutableListOf()
    var responseToPieChart: MutableList<CategoriaModel> = mutableListOf()

    private val _ingresosListLiveData = MutableLiveData<List<BarEntry>>()
    val ingresosListLiveData: LiveData<List<BarEntry>> get() = _ingresosListLiveData

    private val _egresosListLiveData = MutableLiveData<List<BarEntry>>()
    val egresosListLiveData: LiveData<List<BarEntry>> get() = _egresosListLiveData

    private val _pieListLiveData = MutableLiveData<List<PieEntry>>()
    val pieListLiveData: LiveData<List<PieEntry>> get() = _pieListLiveData

    private fun setResponseAPI(_response: List<DataEntry>) {
        responseToBarChart.clear()
        responseToBarChart.addAll(_response)
    }

    private fun setResponseAPIPieChart(_response: List<CategoriaModel>) {
        responseToPieChart.clear()
        responseToPieChart.addAll(_response)
    }

    fun onCreate(userId: String) {
        viewModelScope.launch {
            try {
                val responseDataBarChart = repository.getDataGraph(userId)
                if (responseDataBarChart.isNotEmpty()) {
                    Log.i("RESPONSE",responseDataBarChart.toString())
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


}
