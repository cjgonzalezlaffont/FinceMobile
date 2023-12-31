package com.example.fince.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData: LiveData<String> = _responseLiveData

    fun verificar(mail : String) {

        viewModelScope.launch {
            setIsLoading(true)
            try {
                _responseLiveData.value =  repository.validateMail(mail)
            } catch (e : Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
            setIsLoading(false)
        }
    }
}