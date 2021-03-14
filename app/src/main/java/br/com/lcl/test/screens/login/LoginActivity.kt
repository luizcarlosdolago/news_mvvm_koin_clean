package br.com.lcl.test.screens.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import br.com.lcl.test.screens.news.MainActivity
import br.com.lcl.test.R
import br.com.lcl.test.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(this.layoutInflater)
        setContentView(this.binding.root)
        configureListeners()
    }

    override fun onResume() {
        super.onResume()
        this.loginViewModel.stateLiveDate.observe(this, {
            when(it) {
                is LoginState.Init -> initState()
                is LoginState.Loading -> loadingState()
                is LoginState.Ready -> readyState()
                is LoginState.Success -> successState()
                is LoginState.Error -> errorState(it)
            }
        })
    }

    private fun configureListeners() {
        this.binding.tilUsername.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                this@LoginActivity.loginViewModel.username = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        this.binding.tilPassword.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                this@LoginActivity.loginViewModel.password = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        this.binding.btLogin.setOnClickListener {
            this.loginViewModel.loginButtonClicked()
        }
    }

    private fun initState() {
        this.binding.pbLoading.visibility = View.GONE
        this.binding.btLogin.isEnabled = false
        this.binding.cbSaveData.isEnabled = true
        this.binding.tilUsername.editText?.setText("devmobile")
        this.binding.tilPassword.editText?.setText("uC&+}H4wg?rYbF:")
    }

    private fun loadingState() {
        this.binding.btLogin.visibility = View.GONE
        this.binding.cbSaveData.isEnabled = false
        this.binding.tilUsername.isEnabled = false
        this.binding.tilPassword.isEnabled = false
        this.binding.tilPassword.error = null
        this.binding.pbLoading.visibility = View.VISIBLE
    }

    private fun readyState() {
        this.binding.btLogin.isEnabled = true
        this.binding.cbSaveData.isEnabled = true
        this.binding.pbLoading.visibility = View.GONE
        this.binding.btLogin.visibility = View.VISIBLE
        this.binding.tilPassword.error = null
    }

    private fun successState() {
        goToMainScreen()
    }

    private fun errorState(state: LoginState.Error) {
        this.binding.btLogin.isEnabled = false
        this.binding.pbLoading.visibility = View.GONE
        this.binding.btLogin.visibility = View.VISIBLE
        this.binding.tilUsername.isEnabled = true
        this.binding.tilPassword.isEnabled = true
        this.binding.cbSaveData.isEnabled = true

        Log.d(LoginActivity::class.java.simpleName, "#### error => ${state.error}")
        if (state.error is HttpException) {
            val httpException = state.error as HttpException
            if (httpException.response()?.code() == 401) {
                this.binding.tilPassword.error = getString(R.string.text_error_credentials)
                return
            }
        }
        Snackbar.make(this.binding.btLogin, getString(R.string.text_error_generic), Snackbar.LENGTH_SHORT).show()
    }

    private fun goToMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.ivLogoApp, "logo_app_transition")
        startActivity(intent, options.toBundle())
        this.finish()
    }

}