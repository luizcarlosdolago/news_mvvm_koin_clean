package br.com.lcl.test

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import br.com.lcl.test.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        loadKoin()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun loadKoin() {
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(allModules)
        }
    }
}