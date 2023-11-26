package com.example.fince.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.ObjetivoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgregarObjetivoViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData: LiveData<String> = _responseLiveData

    fun nuevoObjetivo(userId : String, objetivo : ObjetivoModel){
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.createObjetive(userId, objetivo)
            } catch (e : Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }

    fun editarObjetivo(usuarioId: String, objetivo: ObjetivoModel) {
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.editObjeetive(usuarioId, objetivo)
            } catch (e : Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }
}