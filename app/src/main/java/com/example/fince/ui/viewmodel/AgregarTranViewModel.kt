package com.example.fince.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AgregarTranViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    fun nuevaTransaccion(userId : String){
        viewModelScope.launch {
            val response = repository.createTransaction(userId)
        }
    }
}