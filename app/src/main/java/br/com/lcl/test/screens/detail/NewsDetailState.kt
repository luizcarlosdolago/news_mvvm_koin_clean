package br.com.lcl.test.screens.detail

import br.com.lcl.domain.entities.NewsDocumentEntity
import br.com.lcl.domain.entities.NewsEntity

sealed class NewsDetailState {
    object Init : NewsDetailState()
    object Loading : NewsDetailState()
    data class Error(val error: Throwable) : NewsDetailState()
    data class Success(val news: NewsDocumentEntity) : NewsDetailState()
}
