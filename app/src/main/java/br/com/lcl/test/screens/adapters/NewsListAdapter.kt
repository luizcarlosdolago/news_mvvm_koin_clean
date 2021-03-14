package br.com.lcl.test.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.lcl.domain.entities.NewsEntity
import br.com.lcl.test.R
import br.com.lcl.test.screens.callbacks.OnClickAdapterCallback
import com.bumptech.glide.Glide

class NewsListAdapter(private var newsList: List<NewsEntity>) : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    private var callback: OnClickAdapterCallback<NewsEntity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_row_news, parent, false)
        return NewsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind(this.newsList[position])
        holder.itemView.setOnClickListener {
            this.callback?.onItemClicked(this.newsList[position])
        }
    }

    override fun getItemCount(): Int {
        return this.newsList.size
    }

    fun updateList(list: List<NewsEntity>) {
        if (list.isNotEmpty()) {
            this.newsList = list
            notifyDataSetChanged()
        }
    }

    fun setCallback(callback: OnClickAdapterCallback<NewsEntity>) {
        this.callback = callback
    }

    class NewsListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvHat: TextView = view.findViewById(R.id.tvHat)
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val ivPhoto: ImageView = view.findViewById(R.id.ivPhoto)

        fun bind(news: NewsEntity) {
            val circularProgressDrawable = CircularProgressDrawable(this.itemView.rootView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            this.tvHat.text = news.chapeu
            this.tvTitle.text = news.titulo

            Glide
                .with(this.itemView.rootView.context)
                .load(news.imagem)
                .into(this.ivPhoto)
        }
    }

}