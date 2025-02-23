package com.example.mynews.adapter






import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mynews.R
import com.example.mynews.data.Article
import com.example.mynews.ui.headlines.NewsProfileFragment

class HeadlinesAdapter(val newsList: MutableList<Article>) :
    RecyclerView.Adapter<HeadlinesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.headlineTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.headlineSource)
        val newsImageView: ImageView = itemView.findViewById(R.id.headlineImage)
        val headlineTime: TextView = itemView.findViewById(R.id.headlineTime)
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

        holder.itemView.setOnClickListener {
            val fragment = NewsProfileFragment.newInstance(newsItem)
            (holder.itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
