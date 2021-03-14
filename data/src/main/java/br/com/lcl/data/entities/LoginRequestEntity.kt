package br.com.lcl.data.entities

import com.google.gson.annotations.SerializedName

data class LoginRequestEntity(
    @SerializedName("user")
    val username: String ,
    @SerializedName("pass")
    val password: String
)
