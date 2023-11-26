package com.example.fince.data.model

import com.google.gson.annotations.SerializedName

data class ObjetivoResponse(
    @SerializedName("objetivos") val objetivos : List<ObjetivoModel>,
)
