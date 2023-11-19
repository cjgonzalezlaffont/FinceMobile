package com.example.fince.ui.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fince.data.FinceRepository
import com.example.fince.data.model.ActivoModel
import com.example.fince.data.model.Venta
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
    var userId: String = ""

    fun setActivo(activoModel: ActivoModel) {
        activo.value = activoModel
    }

    // Función para realizar la compra
    fun comprarActivo() {

        /* Lo que recibe el endpoint
        {
          "simbolo": "AAPL",
          "nombre": "Compra de activos",
          "tipo": "cedears",
          "valorDeCompra": "1000",
          "fechaDeCompra": "8/11/2023",
          "categoriaId" : "",
          "cantidad": 100,
          "activoId": ""
           }
        */

        viewModelScope.launch {
            try {
                // Hacer la llamada al repositorio para la compra
                val response = if (activo.value != null) {

                    //actualizo los datos activo para realizar la compra

                    var activoActual : ActivoModel = activo.value!!
                    activoActual.cantidad = cantidadDeCompra.value!!
                    activoActual.fechaDeCompra = obtenerFechaActualEnFormato()
                    activoActual.valorDeCompra = activoActual.valorActual!!
                    repository.buyAsset(userId, activoActual!!)

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

                val response = if (
                    activo.value != null &&
                    cantidadDeVenta.value!!.toInt()>0 &&
                    precioDeVenta.value!!.toFloat()>0)

                {
                    var activoActual = activo.value!!
                    val venta = Venta(
                        activoId = activoActual?.activoId ?: "",
                        cantidad = cantidadDeVenta.value ?: 0,
                        precioDeVenta = precioDeVenta.value.toString() ?: ""
                    )

                   repository.sellAsset(userId, venta!!)
                } else {
                    400
                }

                // Manejar la respuesta del repositorio según tu lógica
                if (response == 200) {
                    // Éxito en la venta
                } else {
                    // Manejar el error del repositorio

                }
            } catch (e: Exception) {
                // Manejar errores de red u otros errores
            }
        }
    }

    fun obtenerFechaActualEnFormato(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = Date()
        return formato.format(fechaActual)
    }

}
