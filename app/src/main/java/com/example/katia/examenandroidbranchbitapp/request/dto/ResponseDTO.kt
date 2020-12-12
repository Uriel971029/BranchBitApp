package com.example.katia.examenandroidbranchbitapp.request.dto

import com.google.gson.annotations.SerializedName

data class ResponseDTO(
    @SerializedName("data")  var data: DataDTO,
    @SerializedName("code")  var serverCode: Int,
    @SerializedName("success")  var message: Boolean
)