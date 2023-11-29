package com.example.fince.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.SuccessfulModel
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserRegisterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodigoVerificacionViewModel @Inject constructor(
    private val repository: FinceRepository
): ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _codeLiveData = MutableLiveData<Int>()
    val codeLiveData: LiveData<Int> = _codeLiveData

    fun codigoVerificacion(mail : String) {
        viewModelScope.launch {
            try {
                _codeLiveData.value = repository.sendAuthCode(mail)!!.authCode
            } catch (e : Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }

    private val _responseLiveData = MutableLiveData<UserModel>()
    val responseLiveData: LiveData<UserModel> = _responseLiveData

    fun registrar(user : UserModel) {

        viewModelScope.launch {
            setIsLoading(true)
            try {
                _responseLiveData.value =  repository.userRegister(user)
            } catch (e : Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
            setIsLoading(false)
        }
    }
}