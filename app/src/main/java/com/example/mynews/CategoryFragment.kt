package com.example.mynews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.adapter.HeadlinesAdapter
import com.example.mynews.databinding.FragmentCategoryBinding
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HeadlinesAdapter
    private var category: String? = null
    private val compositeDisposable = CompositeDisposable()

    private val newsDao by lazy { AppDatabase.getDatabase(requireContext()).newsDao() }

    private val newsApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(ARG_CATEGORY)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        recyclerView = binding.categoryRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Инициализируем адаптер с пустым списком
        adapter = HeadlinesAdapter(mutableListOf())
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadNews()
    }

    private fun loadNews() {
        category?.let { categoryValue ->
            val disposable: Disposable = newsApiService.getTopHeadlines(category = categoryValue, apiKey = "8e3d4af8b176486cad2ea977f8a2d01c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newsResponse ->
                    val articles = newsResponse.articles
                    adapter.newsList.clear()
                    adapter.newsList.addAll(articles)
                    adapter.notifyDataSetChanged()
                }, { error ->
                    println(error)
                    Toast.makeText(context, "Error loading news", Toast.LENGTH_SHORT).show()
                })

            compositeDisposable.add(disposable)
        } ?: run {
            println("Category is null")
            Toast.makeText(context, "Category is null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_CATEGORY, category)
                }
            }
    }
}

