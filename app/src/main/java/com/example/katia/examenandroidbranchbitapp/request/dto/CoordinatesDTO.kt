package com.example.katia.examenandroidbranchbitapp.request.dto

import com.google.gson.annotations.SerializedName

data class CoordinatesDTO(
    @SerializedName("lat") var latitude: Double,
    @SerializedName("log") var longitude: Double
)