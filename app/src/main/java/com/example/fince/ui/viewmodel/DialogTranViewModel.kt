package com.example.fince.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.Transaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogTranViewModel @Inject constructor(
private val repository: FinceRepository
) : ViewModel() {
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData: LiveData<String> = _responseLiveData

    fun borrarTransaccion(userId : String, tranId : Transaccion){
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.deleteTransaction(userId, tranId)
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }
}