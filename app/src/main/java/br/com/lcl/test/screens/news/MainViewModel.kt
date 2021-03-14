package br.com.lcl.test.screens.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.lcl.domain.common.NoParams
import br.com.lcl.domain.usecases.GetNewsUseCase
import br.com.lcl.test.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val getNewsUseCase: GetNewsUseCase) : BaseViewModel() {

    var stateLiveDate = MutableLiveData<MainState>(MainState.Init)

    fun load() {
        this.stateLiveDate.value = MainState.Loading
        this.addDisposable(this.getNewsUseCase.call(params = NoParams())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ itAllNews ->
                this.stateLiveDate.value = MainState.Success(itAllNews)
            }, {
                this.stateLiveDate.value = MainState.Error(it)
            }, {
                Log.d(MainViewModel::class.java.simpleName, "#### onCompleted")
            }))
    }

}