package br.com.lcl.data.api

import br.com.lcl.data.entities.LoginRequestEntity
import br.com.lcl.domain.entities.LoginResultEntity
import br.com.lcl.domain.entities.NewsDocumentEntity
import br.com.lcl.domain.entities.NewsEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.*

interface RemoteNewsApi {

    @POST("login")
    fun login(@Body request: LoginRequestEntity) : Observable<LoginResultEntity>

    @GET("news")
    @Headers("auth: required")
    fun getNews() : Flowable<List<NewsEntity>>

    @GET("news/{id}")
    @Headers("auth: required")
    fun getNewsDetailById(@Path("id") id: String) : Observable<List<NewsDocumentEntity>>

}