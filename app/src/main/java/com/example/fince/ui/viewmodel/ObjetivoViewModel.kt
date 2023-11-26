package com.example.fince.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.ObjetivoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObjetivoViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listaObjetivos = MutableLiveData<List<ObjetivoModel>>()
    val listaObjetivos: LiveData<List<ObjetivoModel>> = _listaObjetivos

    fun setObjetivoList(listaObjetivos : List<ObjetivoModel>) {
        _listaObjetivos.value = listaObjetivos
    }

    private val _errorData = MutableLiveData<String>()
    val errorData : LiveData<String> = _errorData

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onCreate(userId : String) {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val response = repository.getAllObjetives(userId)
                setObjetivoList(response.objetivos)
            } catch (e: Exception) {
                _errorData.value = "Error: ${e.message}"
            }
            setIsLoading(false)
        }
    }

    private val _responseData = MutableLiveData<String>()
    val responseData: LiveData<String> = _responseData

    fun deleteObjective(userId : String, objetivo : ObjetivoModel) {
        viewModelScope.launch {
            try {
                _responseData.value = repository.deleteObjective(userId, objetivo.id)?.message
                onCreate(userId)
                val currentList = _listaObjetivos.value.orEmpty().toMutableList()
                currentList.remove(objetivo)
                setObjetivoList(currentList)
            } catch (e: Exception) {
                _errorData.value = "Error: ${e.message}"
            }
        }
    }
    fun clearError() {
        _errorData.value = ""
    }

    fun clearResponse() {
        _responseData.value = ""
    }
}