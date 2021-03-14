package br.com.lcl.domain.entities

import com.google.gson.annotations.SerializedName

data class NewsDocumentEntity (
    @SerializedName("documento")
    val documento: DocumentEntity? = null
)

data class DocumentEntity (
    @SerializedName("url")
    val url: String? = null,

    @SerializedName("source")
    val source: String? = null,

    @SerializedName("produto")
    val produto: String? = null,

    @SerializedName("editoria")
    val editoria: String? = null,

    @SerializedName("subeditoria")
    val subeditoria: String? = null,

    @SerializedName("titulo")
    val titulo: String? = null,

    @SerializedName("credito")
    val credito: String? = null,

    @SerializedName("datapub")
    val datapub: String? = null,

    @SerializedName("horapub")
    val horapub: String? = null,

    @SerializedName("linhafina")
    val linhafina: String? = null,

    @SerializedName("imagem")
    val imagem: String? = null,

    @SerializedName("thumbnail")
    val thumbnail: String? = null,

    @SerializedName("creditoImagem")
    val creditoImagem: String? = null,

    @SerializedName("legendaImagem")
    val legendaImagem: String? = null,

    @SerializedName("origem")
    val origem: String? = null,

    @SerializedName("id:")
    val id: String? = null,

    @SerializedName("corpoformatado")
    val corpoformatado: String? = null
)

