package br.com.lcl.domain.entities

import com.google.gson.annotations.SerializedName

data class NewsEntity (
    @SerializedName("id_documento")
    val idDocumento: String? = null,

    @SerializedName("chapeu")
    val chapeu: String? = null,

    @SerializedName("titulo")
    val titulo: String? = null,

    @SerializedName("linhaFina")
    val linhaFina: String? = null,

    @SerializedName("dataHoraPublicacao")
    val dataHoraPublicacao: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("imagem")
    val imagem: String? = null,

    @SerializedName("source")
    val source: String? = null,

    @SerializedName("credito")
    val credito: String? = null,

    @SerializedName("imagemCredito")
    val imagemCredito: String? = null
)
