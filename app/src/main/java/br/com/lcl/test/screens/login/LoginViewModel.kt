package br.com.lcl.test.screens.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.lcl.domain.usecases.LoginUseCase
import br.com.lcl.domain.usecases.LoginUseParams
import br.com.lcl.test.common.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val prefs: SharedPreferences,
    private val sessionUser: SessionUser) : BaseViewModel() {

    var stateLiveDate = MutableLiveData<LoginState>(LoginState.Init)

    var username: String = ""
        set(value) {
            field = value
            checkButtonState()
        }

    var password: String = ""
        set(value) {
            field = value
            checkButtonState()
        }

    var isSaveData: Boolean = true
        set(value) {
            field = value
        }


    private fun checkButtonState() {
        if (this.username.isNotEmpty() && this.password.isNotEmpty()) {
            this.stateLiveDate.value = LoginState.Ready
        }
        else {
            this.stateLiveDate.value = LoginState.Init
        }
    }

    private fun saveData(token: String) {
        val prefsEdit = this.prefs.edit()
        var usernameValue = this.username
        var passwordValue = this.password
        if (this.isSaveData) {
            usernameValue = ""
            passwordValue = ""
        }
        prefsEdit.putString(PREF_USERNAME, usernameValue)
        prefsEdit.putString(PREF_PASSWORD, passwordValue)
        prefsEdit.apply()

        this.sessionUser.token = token
    }

    fun loginButtonClicked() {
        this.stateLiveDate.value = LoginState.Loading
        this.addDisposable(this.loginUseCase.call(LoginUseParams(username = this.username, password = this.password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ itToken ->
                saveData(itToken)
                this.stateLiveDate.value = LoginState.Success
            }, {
                this.stateLiveDate.value = LoginState.Error(it)
            }, {
                Log.d(LoginViewModel::class.java.simpleName, "#### onCompleted")
            }))
    }

}