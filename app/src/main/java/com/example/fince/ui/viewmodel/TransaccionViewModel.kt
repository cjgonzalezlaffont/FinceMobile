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
    var transaccionList: LiveData<List<Transaccion>> = _transaccionList

    fun setTransaccionList (transaccionList: List<Transaccion>) {
        _transaccionList.value = transaccionList
        this.transaccionList = _transaccionList
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onCreate(userId : String){

        viewModelScope.launch {
            setIsLoading(true)
            try {
                val transaccionList =  repository.getAllTransactions(userId).transacciones

                if (!transaccionList.isEmpty()) {
                    setTransaccionList(transaccionList)
                }
            } catch (e: Exception) {
                Log.e("TransaccionViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }
}