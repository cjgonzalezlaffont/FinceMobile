package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.ActivoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarteraViewModel @Inject constructor(
    private val repository: FinceRepository
) : ViewModel() {


    private val _carteraList = MutableLiveData<List<ActivoModel>>()
    val carteraList: LiveData<List<ActivoModel>> = _carteraList

    fun setTransaccionList (carteraList: List<ActivoModel>) {
        _carteraList.value = carteraList
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onCreate(userId : String){
        viewModelScope.launch {
            setIsLoading(true)
            try {
                val carteraList =  repository.getPortfolio(userId).portfolio

                if (carteraList.isNotEmpty()) {
                    setTransaccionList(carteraList)
                }
            } catch (e: Exception) {
                Log.e("CarteraViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }

    suspend fun getUpdatedCarteraList(userId: String): List<ActivoModel> {
        return repository.getPortfolio(userId).portfolio
    }


}