package br.com.lcl.domain.usecases

import br.com.lcl.domain.common.UseCaseObservable
import br.com.lcl.domain.entities.NewsDocumentEntity
import br.com.lcl.domain.repositories.NewsRepository
import io.reactivex.Observable

class GetNewsDetailUseCase(private val repository: NewsRepository) : UseCaseObservable<NewsDocumentEntity?, GetNewsDetailUseCaseParams> {
    override fun call(params: GetNewsDetailUseCaseParams): Observable<NewsDocumentEntity?> {
        return this.repository.getNewsDetailById(params.id).map { it.firstOrNull() }
    }
}

data class GetNewsDetailUseCaseParams(val id: String)