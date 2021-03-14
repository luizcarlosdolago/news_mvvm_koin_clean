package br.com.lcl.test.screens.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityOptionsCompat
import br.com.lcl.test.R
import br.com.lcl.test.databinding.ActivitySplashScreenBinding
import br.com.lcl.test.screens.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivitySplashScreenBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        registerSchedulle()
    }

    private fun registerSchedulle() {
        Handler(Looper.getMainLooper()).postDelayed({
            routeToPage()
        }, 1500)
    }

    private fun routeToPage() {
        val intent = Intent(this, LoginActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.ivLogoApp, "logo_app_transition")
        startActivity(intent, options.toBundle())
        this.finish()
    }

}