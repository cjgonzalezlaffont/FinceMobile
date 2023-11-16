package com.example.fince.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.CategoriaModel
import com.example.fince.data.model.Transaccion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriaViewModel @Inject constructor(
    private val repository: FinceRepository

) : ViewModel() {
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

                if (!categoriaList.isEmpty()) {
                    setCategoriaList(categoriaList)
                }
            } catch (e: Exception) {
                Log.e("TransaccionViewModel", "Error: ${e.message}")
            }
            setIsLoading(false)
        }
    }
}