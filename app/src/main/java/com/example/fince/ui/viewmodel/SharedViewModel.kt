package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: FinceRepository
)  : ViewModel() {

    private val _response = MutableLiveData<UserModel>()
    val response: LiveData<UserModel> = _response

    fun getUser(userId:String){
        viewModelScope.launch {
            try {
                _response.value = repository.getUserById(userId)
            } catch (e:Exception){
                Log.e("SharedViewModel",e.message.toString())
            }
        }
    }

    private val _isDarkMode = MutableLiveData<Boolean>()
    var isDarkMode: LiveData<Boolean> = _isDarkMode

    fun setDarkMode(isDarkMode: Boolean) {
        _isDarkMode.value = isDarkMode
    }
}