package com.example.fince.ui.viewmodel

import SingleLiveEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OlvideContrasenaViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    // Utiliza SingleLiveEvent para manejar eventos únicos
    private val _responseLiveData = SingleLiveEvent<UserModel>()
    val responseLiveData: LiveData<UserModel> = _responseLiveData

    fun buscarDatos(mail: String) {
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val user = repository.findUserByMail(mail)
                _responseLiveData.value = user
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
            setIsLoading(false)
        }
    }
}
