package br.com.lcl.test.screens.news

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.lcl.domain.entities.NewsEntity
import br.com.lcl.test.R
import br.com.lcl.test.common.PARAM_NEWS
import br.com.lcl.test.databinding.ActivityMainBinding
import br.com.lcl.test.screens.adapters.NewsListAdapter
import br.com.lcl.test.screens.callbacks.OnClickAdapterCallback
import br.com.lcl.test.screens.detail.NewsDetailActivity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(this.binding.root)
        configureRecyclerView()
        configureObservers()
    }

    override fun onStart() {
        super.onStart()
        loadingData()
    }

    private fun configureRecyclerView() {
        this.binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun configureObservers() {
        this.mainViewModel.stateLiveDate.observe(this, {
            when(it) {
                is MainState.Init -> initState()
                is MainState.Loading -> loadingState()
                is MainState.Success -> successState(it)
                is MainState.Error -> errorState(it)
            }
        })
    }

    private fun loadingData() {
        this.mainViewModel.load()
    }

    private fun initState() {
        this.binding.recyclerView.visibility = View.GONE
        this.binding.pbLoading.visibility = View.GONE
    }

    private fun loadingState() {
        this.binding.recyclerView.visibility = View.GONE
        this.binding.pbLoading.visibility = View.VISIBLE
    }

    private fun successState(state: MainState.Success) {
        val adapter = NewsListAdapter(state.news)
        adapter.setCallback(callback = object : OnClickAdapterCallback<NewsEntity> {
            override fun onItemClicked(item: NewsEntity) {
                goToNewsDetails(item)
            }
        })
        this.binding.pbLoading.visibility = View.GONE
        this.binding.recyclerView.visibility = View.VISIBLE
        this.binding.recyclerView.adapter = adapter
    }

    private fun errorState(state: MainState.Error) {
        Snackbar.make(this.binding.recyclerView, getString(R.string.text_error_generic), Snackbar.LENGTH_SHORT).show()
    }

    private fun goToNewsDetails(item: NewsEntity) {
        item.idDocumento?.let { itDocument ->
            startActivity(Intent(this@MainActivity, NewsDetailActivity::class.java).apply {
                putExtra(PARAM_NEWS, itDocument)
            })
        }
    }

}