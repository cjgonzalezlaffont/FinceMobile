package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class TransaccionModel(
    @SerializedName("transactions") val transacciones: List<Transaccion>,
    @SerializedName("incomeAmount") val incomeAmount: Float,
    @SerializedName("expenseAmount") val expenseAmount: Float
    )
