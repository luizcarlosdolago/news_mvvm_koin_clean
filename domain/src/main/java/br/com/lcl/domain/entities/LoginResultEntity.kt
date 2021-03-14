package br.com.lcl.domain.entities

import com.google.gson.annotations.SerializedName

data class LoginResultEntity(
    @SerializedName("token")
    val token: String
    )
