package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {
    private val _response = MutableLiveData<UserResponse>()
    val response: LiveData<UserResponse> = _response

    fun setUser(user:UserResponse){
        _response.value=user
    }

    fun onCreate(userId:String){
        viewModelScope.launch {
            try {
                val response=repository.getUserById(userId)
                if(!(response==null)){
                    setUser(response);
                }
            } catch (e:Exception){
                Log.e("UserViewModel",e.message.toString())
            }
        }
    }
}