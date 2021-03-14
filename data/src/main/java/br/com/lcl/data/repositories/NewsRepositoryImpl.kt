package br.com.lcl.data.repositories

import br.com.lcl.data.datasources.news.NewsDataSource
import br.com.lcl.domain.entities.LoginResultEntity
import br.com.lcl.domain.entities.NewsDocumentEntity
import br.com.lcl.domain.entities.NewsEntity
import br.com.lcl.domain.repositories.NewsRepository
import io.reactivex.Flowable
import io.reactivex.Observable

class NewsRepositoryImpl(private val datasource: NewsDataSource) : NewsRepository {

    override fun login(username: String, password: String): Observable<LoginResultEntity> {
        return this.datasource.login(username, password)
    }

    override fun getNews(): Flowable<List<NewsEntity>> {
        return this.datasource.getNews()
    }

    override fun getNewsDetailById(id: String): Observable<List<NewsDocumentEntity>> {
        return this.datasource.getNewsDetailById(id)
    }
}