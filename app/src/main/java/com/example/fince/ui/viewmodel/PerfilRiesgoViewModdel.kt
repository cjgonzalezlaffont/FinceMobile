package com.example.fince.ui.viewmodel

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
class PerfilRiesgoViewModdel @Inject constructor(
    private val repository : FinceRepository
): ViewModel() {


    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<UserModel>()
    val responseLiveData: LiveData<UserModel> = _responseLiveData

    fun actualizar(user : UserModel) {
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.updateUser(user)
            } catch (e : Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }
}