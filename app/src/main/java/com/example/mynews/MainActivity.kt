package com.example.mynews

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mynews.data.NewsItem
import com.example.mynews.data.NewsRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var newsRepository: NewsRepository
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Замените на ваш layout

        resultTextView = findViewById(R.id.result_tv) // Замените на id вашего TextView
        val database = DatabaseBuilder.getDatabase(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            newsRepository = NewsRepositoryImpl(database = database)
        } // Создание экземпляра репозитория

        // Вызов запроса в корутине
        lifecycleScope.launch {
            try {
                val newsList = newsRepository.getTopHeadlines()
                handleNewsData(newsList) // Обработка полученных данных
            } catch (e: Exception) {
                handleError(e) // Обработка ошибок
            }
        }
    }

    // Обработка списка новостей
    private fun handleNewsData(newsList: List<NewsItem>) {
        if (newsList.isNotEmpty()) {
            val firstNews = newsList[0]
            val resultText = """
                Title: ${firstNews.title}
                Description: ${firstNews.description}
                URL: ${firstNews.url}
            """.trimIndent()

            resultTextView.text = resultText
            Log.d("NewsData", "First news item: $resultText")

        } else {
            Log.d("NewsData", "News list is empty.")
            resultTextView.text = "News list is empty"
        }
    }

    // Обработка ошибок
    private fun handleError(e: Exception) {
        Log.e("NewsError", "Error fetching news: ${e.message}", e)
        resultTextView.text = "Error fetching news: ${e.message}"
    }
}
