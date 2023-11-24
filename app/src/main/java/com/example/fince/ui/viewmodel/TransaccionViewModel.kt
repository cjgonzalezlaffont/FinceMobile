package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.SuccessfulModel
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onCreate(userId : String) {

        viewModelScope.launch {
            setIsLoading(true)
            try {
                val transaccionList = repository.getAllTransactions(userId).transacciones

                if (!transaccionList.isEmpty()) {
                    setTransaccionList(transaccionList)
                }
            } catch (e: Exception) {
                Log.e("TransaccionViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }

    private val _isTransactionDeleted = MutableLiveData<Boolean>()
    val isTransactionDeleted: LiveData<Boolean> = _isTransactionDeleted

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData: LiveData<String> = _responseLiveData

    fun deleteTransaction(userId : String, tran : Transaccion) {
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.deleteTransaction(userId, tran)?.message
                onCreate(userId)
                val currentList = _transaccionList.value.orEmpty().toMutableList()
                currentList.remove(tran)
                setTransaccionList(currentList)
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }

    fun clearError() {
        _errorLiveData.value = ""
    }

    fun clearResponse() {
        _responseLiveData.value = ""
    }
}