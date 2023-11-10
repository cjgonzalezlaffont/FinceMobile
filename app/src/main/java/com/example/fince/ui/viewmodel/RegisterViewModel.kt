package com.example.fince.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fince.data.FinceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {

}