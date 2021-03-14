package br.com.lcl.domain.usecases

import br.com.lcl.domain.common.UseCaseObservable
import br.com.lcl.domain.repositories.NewsRepository
import io.reactivex.Observable

class LoginUseCase(private val repository: NewsRepository) :
    UseCaseObservable<String, LoginUseParams> {
    override fun call(params: LoginUseParams): Observable<String> {
        return this.repository.login(params.username, params.password).map { it.token }
    }
}

data class LoginUseParams(val username: String, val password: String)