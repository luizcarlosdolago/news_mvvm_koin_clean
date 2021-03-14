package br.com.lcl.test.common

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.ioThread() : Observable<T>{
    this.subscribeOn(Schedulers.io())
    this.observeOn(AndroidSchedulers.mainThread())
    return this
}