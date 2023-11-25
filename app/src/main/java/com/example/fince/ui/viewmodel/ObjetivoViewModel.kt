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
class ObjetivoViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseData = MutableLiveData<List<ObjetivoModel>>()
    val responseData: LiveData<List<ObjetivoModel>> = _responseData

    private val _errorData = MutableLiveData<String>()
    val errorData : LiveData<String> = _errorData
    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }
    fun onCreate(userId : String) {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                _responseData.value = repository.getAllObjetives(userId)
            } catch (e: Exception) {
                _errorData.value = "Error: ${e.message}"
            }
            setIsLoading(false)
        }
    }
}