package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.SuccessfulModel
import com.example.fince.data.model.Transaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AgregarCatViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData: LiveData<String> = _responseLiveData

    fun nuevaCategoria(userId : String, categoria : CategoriaModel) {
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.createCategorie(userId, categoria)?.message
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }

    fun editarCategoria(userId : String, categoriaReq: CategoriaModel) {
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.editCategorie(userId, categoriaReq)?.message
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }
}