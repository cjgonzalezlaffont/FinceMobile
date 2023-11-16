package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class PortfolioModel(
    @SerializedName("portfolio") val portfolio: List<ActivoModel>,
    @SerializedName("totalInvestments") val totalInvestments: Float,
)