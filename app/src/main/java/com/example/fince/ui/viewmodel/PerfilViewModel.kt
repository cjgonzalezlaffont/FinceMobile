package com.example.fince.ui.viewmodel

import android.util.Log
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
class PerfilViewModel @Inject constructor(
    private val repository: FinceRepository
): ViewModel(){

    private val _response = MutableLiveData<UserModel>()
    val response: LiveData<UserModel> = _response

    fun getUser(userId:String){
        viewModelScope.launch {
            try {
                _response.value = repository.getUserById(userId)
            } catch (e:Exception){
                Log.e("PerfilViewModel",e.message.toString())
            }
        }
    }

    private val _updateResponse = MutableLiveData<UserModel>()
    val updateResponse: LiveData<UserModel> = _updateResponse

    fun updateUser(user : UserModel) {
        viewModelScope.launch {
            try {
                _response.value = repository.updateUser(user)
            } catch (e:Exception){
                Log.e("PerfilViewModel",e.message.toString())
            }
        }
    }
}