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
class UserViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {
    val response = MutableLiveData<UserModel>()

    fun setUser(user:UserModel){
        response.value=user
    }

    fun onCreate(userId:String): UserModel{
        var response =UserModel("","","","","",0,0,1)
        viewModelScope.launch {
            try {
                response=repository.getUserById(userId)
                if(!(response==null)){
                    setUser(response);
                }
            } catch (e:Exception){
                Log.e("UserViewModel",e.message.toString())
            }
        }
        return response
    }
}