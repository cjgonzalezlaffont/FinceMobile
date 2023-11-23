package com.example.fince.ui.viewmodel

import android.util.Log
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
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

    suspend fun getCedears(): List<StockModel>{
        return viewModelScope.async {
            try{
                val result = repository.getCedears()
                _response.postValue(result)
                result
            }catch (e: Exception){
                emptyList()
            }
        } .await()
    }
    suspend fun getStocks(): List<StockModel>{
        return viewModelScope.async {
            try{
                val result = repository.getCedears()
                _response.postValue(result)
                result
            }catch (e: Exception){
                emptyList()
            }
        } .await()
    }
    suspend fun getGovernmentBonds(): List<StockModel>{
        return viewModelScope.async {
            try{
                val result = repository.getGovernmentBonds()
                _response.postValue(result)
                result
            }catch (e: Exception){
                emptyList()
            }
        } .await()
    }
    suspend fun getInvestmentFunds(): List<StockModel>{
        return viewModelScope.async {
            try{
                val result = repository.getInvestmentFund()
                _response.postValue(result)
                result
            }catch (e: Exception){
                emptyList()
            }
        } .await()
    }
    fun onCreate(){
        _response.value = emptyList()
        onCreateCedears()
        onCreateStocks()
        onCreateGovernmentBonds()
        onCreateCorporateBonds()
        onCreateInvestmentFunds()
    }

    fun onCreateCedears(){
        setIsLoading(true)
        viewModelScope.launch {
            try {
                val response =  repository.getCedears()
                val currentList = _response.value?.toMutableList() ?: mutableListOf()
                currentList.addAll(response)
                if (!response.isEmpty()) {
                    setStockList(currentList)
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }
    fun onCreateStocks(){
        setIsLoading(true)
        viewModelScope.launch {
            try {
                val response =  repository.getStocks()
                val currentList = _response.value?.toMutableList() ?: mutableListOf()
                currentList.addAll(response)
                if (!response.isEmpty()) {
                    setStockList(currentList)
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }

    fun onCreateGovernmentBonds(){
        setIsLoading(true)
        viewModelScope.launch {
            try {
                val response =  repository.getGovernmentBonds()
                val currentList = _response.value?.toMutableList() ?: mutableListOf()
                currentList.addAll(response)
                if (!response.isEmpty()) {
                    setStockList(currentList)
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }
    fun onCreateCorporateBonds(){
        setIsLoading(true)
        viewModelScope.launch {
            try {
                val response =  repository.getCorporateBonds()
                val currentList = _response.value?.toMutableList() ?: mutableListOf()
                currentList.addAll(response)
                if (!response.isEmpty()) {
                    setStockList(currentList)
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }
    fun onCreateInvestmentFunds(){
        setIsLoading(true)
        viewModelScope.launch {
            try {
                val response =  repository.getInvestmentFund()
                val currentList = _response.value?.toMutableList() ?: mutableListOf()
                currentList.addAll(response)
                if (!response.isEmpty()) {
                    setStockList(currentList)
                }
            } catch (e: Exception) {
                Log.e("StockViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }
    
}
