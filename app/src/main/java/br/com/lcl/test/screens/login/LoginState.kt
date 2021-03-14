package br.com.lcl.test.screens.login

sealed class LoginState() {
    object Init : LoginState()
    object Loading : LoginState()
    object Ready : LoginState()
    object Success : LoginState()
    data class Error(val error: Throwable) : LoginState()
}
