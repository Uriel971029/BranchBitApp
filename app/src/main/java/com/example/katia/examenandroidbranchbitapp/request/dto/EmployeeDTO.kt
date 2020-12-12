package com.example.katia.examenandroidbranchbitapp.request.dto

import android.location.LocationManager
import com.google.gson.annotations.SerializedName

 class EmployeeDTO(
     @SerializedName("id") public val id: Int,
     @SerializedName("location") var location: LocationManager,
     @SerializedName("name") val nombre: String,
     @SerializedName("mail") val correo: String
)