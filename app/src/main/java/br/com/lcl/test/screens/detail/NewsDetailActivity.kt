package br.com.lcl.test.screens.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.lcl.test.R
import br.com.lcl.test.common.Helper
import br.com.lcl.test.common.PARAM_NEWS
import br.com.lcl.test.databinding.ActivityNewsDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel


class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private val newsDetailViewModel: NewsDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityNewsDetailBinding.inflate(this.layoutInflater)
        setContentView(this.binding.root)
        configureToolbar()
        configureObservables()
        loadData()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun configureToolbar() {
        setSupportActionBar(this.binding.toolbar)
        supportActionBar?.title = "Detalhe da notícia"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun configureObservables() {
        this.newsDetailViewModel.stateLiveDate.observe(this, {
            when (it) {
                is NewsDetailState.Init -> initState()
                is NewsDetailState.Loading -> loadingState()
                is NewsDetailState.Success -> successState(it)
                is NewsDetailState.Error -> errorState(it)
            }
        })
    }

    private fun loadData() {
        this.intent.extras?.let { itExtras ->
            itExtras.getString(PARAM_NEWS)?.let { itDocument ->
                this.newsDetailViewModel.load(itDocument)
            }
        }
    }

    private fun initState() {
        this.binding.scrollView.visibility = View.GONE
        this.binding.pbLoading.visibility = View.GONE
    }

    private fun loadingState() {
        this.binding.scrollView.visibility = View.GONE
        this.binding.pbLoading.visibility = View.VISIBLE
    }

    private fun successState(state: NewsDetailState.Success) {
        this.binding.pbLoading.visibility = View.GONE
        this.binding.scrollView.visibility = View.VISIBLE
        state.news.documento?.let { itDocument ->
            itDocument.imagem?.let { itUrl ->
                loadPhoto(itUrl)
            }
            this.binding.tvEditing.text = itDocument.editoria
            this.binding.tvTitle.text = itDocument.titulo
            this.binding.tvDateAuthor.text = Helper.formatDateTimeWithAuthor(
                itDocument.datapub,
                itDocument.horapub,
                itDocument.credito
            )
            this.binding.tvBody.text = Helper.formatHtmlText(itDocument.corpoformatado)
        }
    }

    private fun errorState(state: NewsDetailState.Error) {
        Snackbar.make(this.binding.tvTitle, getString(R.string.text_error_generic), Snackbar.LENGTH_SHORT).show()
    }

    private fun loadPhoto(url: String) {
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide
            .with(this)
            .load(url)
            .placeholder(circularProgressDrawable)
            .into(this.binding.ivPhoto)
    }

}