package br.com.lcl.domain.usecases

import br.com.lcl.domain.common.NoParams
import br.com.lcl.domain.common.UseCaseFlowable
import br.com.lcl.domain.entities.NewsEntity
import br.com.lcl.domain.repositories.NewsRepository
import io.reactivex.Flowable

class GetNewsUseCase(private val repository: NewsRepository) : UseCaseFlowable<List<NewsEntity>, NoParams> {
    override fun call(params: NoParams): Flowable<List<NewsEntity>> {
        return this.repository.getNews()
    }
}