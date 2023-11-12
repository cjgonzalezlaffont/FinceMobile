package com.example.fince.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.UserModel
import com.example.fince.data.model.userLoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {
    private var response = CompletableDeferred<UserModel>()
    suspend fun login(user: userLoginModel): UserModel {
        return withContext(Dispatchers.IO) {
            repository.userLogin(user)
        }
    }
}