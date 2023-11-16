package com.example.fince.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.ActivoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradingViewModel @Inject constructor(
    private val repository: FinceRepository
): ViewModel() {
    // Variables para la cantidad y precio
    var cantidadDeCompra: MutableLiveData<Int> = MutableLiveData(0)
    var cantidadDeVenta: MutableLiveData<Int> = MutableLiveData(0)
    var precioDeVenta: MutableLiveData<Double> = MutableLiveData(0.0)
    var activo: MutableLiveData<ActivoModel> = MutableLiveData()
    private var userId: String = ""

    fun setActivo(activoModel: ActivoModel) {
        activo.value = activoModel
    }
    fun setUserId(userId: String) {
        this.userId = userId
    }
    // Función para realizar la compra
    fun comprarActivo() {

        viewModelScope.launch {
            try {
                // Hacer la llamada al repositorio para la compra
                val response = if (activo.value != null) {
                    repository.buyAsset(userId, activo.value!!)
                } else {
                    // Manejar el caso cuando activo.value es nulo
                    throw error("no existe el activo?")
                }
                // Manejar la respuesta del repositorio según tu lógica
                if (response == 200) {
                    // Compra exitosa: ir a cartera
                } else {
                    // Ver que hacer
                }
            } catch (e: Exception) {
                // Manejar errores de red u otros errores
            }
        }
    }



    /*
    const assetId = req.body.activoId;
    const quantity = req.body.cantidad;
    const salePrice = req.body.precioDeVenta;
    const userId = req.params.userId;
    */

// Función para realizar la venta
    fun venderActivo() {
        viewModelScope.launch {
            try {
                // Hacer la llamada al repositorio para la venta
                val userId = "123" // Reemplaza con el ID de usuario real

                val response = if (activo.value != null) {
                    repository.sellAsset(userId, activo.value!!)
                } else {
                    // Manejar el caso cuando activo.value es nulo
                    400
                }

                // Manejar la respuesta del repositorio según tu lógica
                if (response == 1) {
                    // Éxito en la venta
                } else {
                    // Manejar el error del repositorio
                }
            } catch (e: Exception) {
                // Manejar errores de red u otros errores
            }
        }
    }

}
