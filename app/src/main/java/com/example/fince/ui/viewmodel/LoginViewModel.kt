package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fince.data.FinceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

    fun onCreate(){
        Log.i("dato", "Creo viewmodel")
    }
}