package br.com.lcl.test.screens.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.lcl.domain.usecases.GetNewsDetailUseCase
import br.com.lcl.domain.usecases.GetNewsDetailUseCaseParams
import br.com.lcl.test.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsDetailViewModel(private val getNewsDetailUseCase: GetNewsDetailUseCase) : BaseViewModel() {
    var stateLiveDate = MutableLiveData<NewsDetailState>(NewsDetailState.Init)

    fun load(id: String) {
        this.stateLiveDate.value = NewsDetailState.Loading
        this.addDisposable(this.getNewsDetailUseCase.call(params = GetNewsDetailUseCaseParams(id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ itNewsDocument ->
                itNewsDocument?.let {
                    setState(NewsDetailState.Success(itNewsDocument))
                } ?: setState(NewsDetailState.Init)
            }, {
                this.stateLiveDate.value = NewsDetailState.Error(it)
            }, {
                Log.d(NewsDetailViewModel::class.java.simpleName, "#### onCompleted")
            }))
    }

    private fun setState(state: NewsDetailState) {
        this.stateLiveDate.value = state
    }
}