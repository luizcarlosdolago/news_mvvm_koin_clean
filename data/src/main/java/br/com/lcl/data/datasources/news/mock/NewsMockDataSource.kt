package br.com.lcl.data.datasources.news.mock

import br.com.lcl.data.datasources.news.NewsDataSource
import br.com.lcl.domain.entities.LoginResultEntity
import br.com.lcl.domain.entities.NewsDocumentEntity
import br.com.lcl.domain.entities.NewsEntity
import io.reactivex.Flowable
import io.reactivex.Observable

class NewsMockDataSource : NewsDataSource {
    override fun login(username: String, password: String): Observable<LoginResultEntity> {
        TODO("Not yet implemented")
    }

    override fun getNews(): Flowable<List<NewsEntity>> {
        TODO("Not yet implemented")
    }

    override fun getNewsDetailById(id: String): Observable<List<NewsDocumentEntity>> {
        TODO("Not yet implemented")
    }
}