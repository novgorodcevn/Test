package com.example.mynews.adapter






import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.R
import com.example.mynews.data.Article

class HeadlinesAdapter(val newsList: MutableList<Article>) :
    RecyclerView.Adapter<HeadlinesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.headlineTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.headlineSource)
        val newsImageView: ImageView = itemView.findViewById(R.id.headlineImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_headline, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val newsItem = newsList[position]
        holder.titleTextView.text = newsItem.title
        holder.descriptionTextView.text = newsItem.description

        // Загрузка изображения с помощью Glide
        Glide.with(holder.itemView.context)
            .load(newsItem.urlToImage)
            .placeholder(R.drawable.ic_launcher_background) // Заглушка
            .error(R.drawable.ic_launcher_background) // Изображение при ошибке
            .into(holder.newsImageView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
