package com.example.katia.examenandroidbranchbitapp.request.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "employee")
data class EmployeeDTO(
    @PrimaryKey
    @SerializedName("id") val id: Int?,
    @Embedded
    @SerializedName("location") var location: CoordinatesDTO,
    @SerializedName("name") var nombre: String,
    @SerializedName("mail") var correo: String
) : Serializable
