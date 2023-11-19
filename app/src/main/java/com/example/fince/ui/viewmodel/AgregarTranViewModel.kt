package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.ErrorResponse
import com.example.fince.data.model.Transaccion
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AgregarTranViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _categoriaList = MutableLiveData<List<CategoriaModel>>()
    val categoriaList: LiveData<List<CategoriaModel>> = _categoriaList

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<Transaccion>()
    val responseLiveData: LiveData<Transaccion> = _responseLiveData

    fun setCategoriaList (categoriaList: List<CategoriaModel>) {
        _categoriaList.value = categoriaList
    }

    fun onCreate(userId : String){

        viewModelScope.launch {
            try {
                val categoriaList =  repository.getAllCategories(userId)

                if (!categoriaList.isEmpty()) {
                    setCategoriaList(categoriaList)
                }
            } catch (e: Exception) {
                Log.e("AgregarTranViewModel", "Error: ${e.message}")
            }
        }
    }

    fun nuevaTransaccion(userId : String, transaccion : Transaccion){
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.createTransaction(userId, transaccion)
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }

}