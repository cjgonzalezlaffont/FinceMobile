package com.example.fince.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
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
    private var response = UserModel("", "", "", "", "", 0,0,0)
    fun registrar(user : UserRegisterModel) : UserModel {

        viewModelScope.launch {
            response =  repository.userRegister(user)
        }
        return response
    }
}