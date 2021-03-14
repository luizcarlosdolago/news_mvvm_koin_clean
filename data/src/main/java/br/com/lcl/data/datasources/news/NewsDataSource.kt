package br.com.lcl.data.datasources.news

import br.com.lcl.domain.entities.LoginResultEntity
import br.com.lcl.domain.entities.NewsDocumentEntity
import br.com.lcl.domain.entities.NewsEntity
import io.reactivex.Flowable
import io.reactivex.Observable

interface NewsDataSource {
    fun login(username: String, password: String) : Observable<LoginResultEntity>
    fun getNews() : Flowable<List<NewsEntity>>
    fun getNewsDetailById(id: String) : Observable<List<NewsDocumentEntity>>
}