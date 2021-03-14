package br.com.lcl.data.datasources.news.remote

import br.com.lcl.data.api.RemoteNewsApi
import br.com.lcl.data.datasources.news.NewsDataSource
import br.com.lcl.data.entities.LoginRequestEntity
import br.com.lcl.domain.entities.LoginResultEntity
import br.com.lcl.domain.entities.NewsDocumentEntity
import br.com.lcl.domain.entities.NewsEntity
import io.reactivex.Flowable
import io.reactivex.Observable

class NewsRemoteDataSource(private val api: RemoteNewsApi) : NewsDataSource {

    override fun login(username: String, password: String): Observable<LoginResultEntity> {
        return this.api.login(LoginRequestEntity(username = username, password = password))
    }

    override fun getNews(): Flowable<List<NewsEntity>> {
        return this.api.getNews()
    }

    override fun getNewsDetailById(id: String): Observable<List<NewsDocumentEntity>> {
        return this.api.getNewsDetailById(id)
    }

}