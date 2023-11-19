package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
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
    var response: MutableList<DataEntry> = mutableListOf()

    private val _ingresosListLiveData = MutableLiveData<List<BarEntry>>()
    val ingresosListLiveData: LiveData<List<BarEntry>> get() = _ingresosListLiveData

    private val _egresosListLiveData = MutableLiveData<List<BarEntry>>()
    val egresosListLiveData: LiveData<List<BarEntry>> get() = _egresosListLiveData

    private val _pieEntriesLiveData = MutableLiveData<List<PieEntry>>()
    val pieEntriesLiveData: LiveData<List<PieEntry>> get() = _pieEntriesLiveData

    private fun setResponseAPI(_response: List<DataEntry>) {
        response.clear()
        response.addAll(_response)
    }

    fun onCreate(userId: String) {
        viewModelScope.launch {
            try {
                val responseData = repository.getDataGraph(userId)
                if (responseData.isNotEmpty()) {
                    Log.i("RESPONSE",responseData.toString())
                    setResponseAPI(responseData)
                    updateLists()
                }
            } catch (e: Exception) {
                Log.e("PrincipalViewModel", "Error: ${e.message}")
            }
        }
    }

    private fun updateLists() {
        var i = 1.0f
        for (data in response) {
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
