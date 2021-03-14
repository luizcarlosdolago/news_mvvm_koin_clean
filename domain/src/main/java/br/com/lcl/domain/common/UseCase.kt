package br.com.lcl.domain.common

import io.reactivex.Flowable
import io.reactivex.Observable

interface UseCaseFlowable<Type, Params> {
    fun call(params: Params) : Flowable<Type>
}

interface UseCaseObservable<Type, Params> {
    fun call(params: Params) : Observable<Type>
}

class NoParams {}