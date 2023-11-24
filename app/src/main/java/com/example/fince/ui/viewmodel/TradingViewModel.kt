package com.example.fince.ui.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
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

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData: LiveData<String> = _responseLiveData

    fun setActivo(activoModel: ActivoModel) {
        activo.value = activoModel
    }

    fun comprarActivo() {

        viewModelScope.launch {
            try {
                if (activo.value != null) {

                    var activoActual : ActivoModel = activo.value!!
                    activoActual.cantidad = cantidadDeCompra.value!!
                    activoActual.fechaDeCompra = obtenerFechaActualEnFormato()
                    activoActual.valorDeCompra = activoActual.valorActual!!
                    _responseLiveData.value = repository.buyAsset(userId, activoActual!!)

                } else {
                    throw error("no existe el activo")
                }
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }

    fun venderActivo() {
        viewModelScope.launch {
            try {
                if (
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
                    _responseLiveData.value = repository.sellAsset(userId, venta!!)
                } else {
                    throw error("No se puede vender activo")
                }
            } catch (e: Exception) {
                _errorLiveData.value = "Error: ${e.message}"
            }
        }
    }

    fun obtenerFechaActualEnFormato(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = Date()
        return formato.format(fechaActual)
    }

}
