package com.example.fince.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.StockModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private var _response = MutableLiveData<List<StockModel>>()
    val response: LiveData<List<StockModel>> = _response

    fun setStockList(stockList: List<StockModel>) {
        _response.value = stockList
    }

    suspend fun getAllInstruments(): List<StockModel> {
        return viewModelScope.async {
            try {
                val result = repository.getAllInstruments()
                _response.postValue(result)  // Actualizar LiveData en el hilo principal
                result
            } catch (e: Exception) {
                // Manejar errores según tus necesidades
                emptyList()  // Devolver una lista vacía o manejar el error de otra manera
            }
        }.await()
    }
}
