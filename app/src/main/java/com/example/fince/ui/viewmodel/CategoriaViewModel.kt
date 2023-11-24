package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.SuccessfulModel
import com.example.fince.data.model.Transaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val repository: FinceRepository

) : ViewModel() {
    var listNoFinanciera: MutableList<CategoriaModel> = mutableListOf()

    private val _categoriaList = MutableLiveData<List<CategoriaModel>>()
    val categoriaList: LiveData<List<CategoriaModel>> = _categoriaList

    fun setCategoriaList (categoriaList: List<CategoriaModel>) {
        _categoriaList.value = categoriaList
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setIsLoading (isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun onCreate(userId : String) {

        viewModelScope.launch {
            setIsLoading(true)
            try {
                val categoriaList = repository.getAllCategories(userId)

                listNoFinanciera.clear()

                if (!categoriaList.isEmpty()) {
                    for (item in categoriaList){
                        if (!item.financiera) {
                            listNoFinanciera.add(item)
                        }
                    }
                    setCategoriaList(listNoFinanciera)
                }
            } catch (e: Exception) {
                Log.e("CategoriaViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData: LiveData<String> = _responseLiveData

    fun deleteCategorie(userId : String, categoria : CategoriaModel) {
        viewModelScope.launch {
            try {
                _responseLiveData.value = repository.deleteCategorie(userId, categoria.id)?.message
                onCreate(userId)
                val currentList = _categoriaList.value.orEmpty().toMutableList()
                currentList.remove(categoria)
                setCategoriaList(currentList)
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }
    fun clearError() {
        _errorLiveData.value = ""
    }

    fun clearResponse() {
        _responseLiveData.value = ""
    }
}