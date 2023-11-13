package com.example.fince.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.StockModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repository: FinceRepository
): ViewModel(){
    private var _response = MutableLiveData<List<StockModel>>()
    val response: LiveData<List<StockModel>> get() = _response
    suspend fun getAllInstruments() {
        viewModelScope.launch {
            _response.value = repository.getAllInstruments()
        }

    }
}

/*fun onCreate() {
        //Aca creo ese objecto
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getQuotesUseCase()

            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false)


            }
        }
    }*/