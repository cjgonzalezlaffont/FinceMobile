package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.Transaccion
import com.example.fince.data.model.TransaccionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransaccionViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _transaccionList = MutableLiveData<List<Transaccion>>()
    val transaccionList: LiveData<List<Transaccion>> = _transaccionList

    fun setTransaccionList (transaccionList: List<Transaccion>) {
        _transaccionList.value = transaccionList
    }

    fun onCreate(userId : String){

        viewModelScope.launch {
            try {
                val transaccionList =  repository.getAllTransactions(userId).transacciones

                if (!transaccionList.isEmpty()) {
                    setTransaccionList(transaccionList)
                }
            } catch (e: Exception) {
                Log.e("TransaccionViewModel", "Error: ${e.message}")
            }
        }
    }
}