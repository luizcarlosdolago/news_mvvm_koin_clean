package br.com.lcl.test.screens.news

import br.com.lcl.domain.entities.NewsEntity

sealed class MainState {
    object Init : MainState()
    object Loading : MainState()
    data class Error(val error: Throwable) : MainState()
    data class Success(val news: List<NewsEntity>) : MainState()
}
