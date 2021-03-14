package br.com.lcl.test.di

import android.content.Context
import android.content.SharedPreferences
import br.com.lcl.data.api.RemoteNewsApi
import br.com.lcl.data.datasources.news.mock.NewsMockDataSource
import br.com.lcl.data.datasources.news.remote.NewsRemoteDataSource
import br.com.lcl.data.repositories.NewsRepositoryImpl
import br.com.lcl.domain.usecases.GetNewsDetailUseCase
import br.com.lcl.domain.usecases.GetNewsUseCase
import br.com.lcl.domain.usecases.LoginUseCase
import br.com.lcl.test.common.SessionUser
import br.com.lcl.test.screens.detail.NewsDetailViewModel
import br.com.lcl.test.screens.login.LoginViewModel
import br.com.lcl.test.screens.news.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.lang.reflect.Array.get

val mNetworkModules = module {
    single(named(RETROFIT_INSTANCE)) {
        createNetworkClient(BASE_URL)
    }
    single(named(API)) {
        (get(named(RETROFIT_INSTANCE)) as Retrofit).create(RemoteNewsApi::class.java)
    }
}

val mRepositoryModules = module {
    single(named("mock")) { NewsMockDataSource() }
    single {
        NewsRemoteDataSource(get(named(API)))
    }
    single{
        NewsRepositoryImpl(datasource = get<NewsRemoteDataSource>())
    }
}

val mUseCases = module {
    factory {
        LoginUseCase(repository = get<NewsRepositoryImpl>())
    }
    factory(named(LOGIN_USE_CASE)) {
        LoginUseCase(repository = get<NewsRepositoryImpl>())
    }
    factory(named(GET_NEWS_USE_CASE)) {
        GetNewsUseCase(repository = get<NewsRepositoryImpl>())
    }
    factory(named(GET_NEWS_DETAIL_USE_CASE)) {
        GetNewsDetailUseCase(repository = get<NewsRepositoryImpl>())
    }
}

val mViewModels = module {
    viewModel {
        LoginViewModel(
            loginUseCase = get(named(LOGIN_USE_CASE)),
            prefs = get(named(SHARED_PREFERENCES)),
            sessionUser = get(named(SESSION_USER)))
    }
    viewModel {
        MainViewModel(getNewsUseCase = get(named(GET_NEWS_USE_CASE)))
    }
    viewModel {
        NewsDetailViewModel(getNewsDetailUseCase = get(named(GET_NEWS_DETAIL_USE_CASE)))
    }
}

val mGlobalModules = module {
    single(named(SESSION_USER)) {
        SessionUser()
    }
    single(named(SHARED_PREFERENCES)) {
        androidContext().getSharedPreferences(
            SHARED_PREFERENCES_PREF,
            Context.MODE_PRIVATE
        )
    }
}

val allModules = mNetworkModules + mRepositoryModules + mUseCases + mViewModels + mGlobalModules

private const val SHARED_PREFERENCES_PREF = "br.com.lcl.test.preferences"
private const val BASE_URL = "https://teste-dev-mobile-api.herokuapp.com/"
private const val RETROFIT_INSTANCE = "Retrofit"
private const val API = "Api"
const val SHARED_PREFERENCES = "shared_preferences"
const val LOGIN_USE_CASE = "loginUseCase"
const val GET_NEWS_USE_CASE = "getNewsUseCase"
const val GET_NEWS_DETAIL_USE_CASE = "getNewsDetailUseCase"
const val SESSION_USER = "session_user"
