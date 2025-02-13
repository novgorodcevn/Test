package com.example.mynews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.adapter.HeadlinesAdapter
import com.example.mynews.viewmodel.HeadlinesModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HeadlinesAdapter
    private var category: String? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_CATEGORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        recyclerView = view.findViewById(R.id.categoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsDao = AppDatabase.getDatabase(requireContext()).newsDao()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY // Устанавливаем уровень логирования
            })
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApiService = retrofit.create(NewsApiService::class.java)

        category?.let { categoryValue ->
            compositeDisposable.add(io.reactivex.rxjava3.core.Observable.fromCallable {
                kotlinx.coroutines.runBlocking {
                    val headlinesModel = HeadlinesModel(newsDao,newsApiService)
                    headlinesModel.getHeadlines(1,categoryValue)
                }
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newsList ->
                    adapter = HeadlinesAdapter(newsList.toMutableList())
                    recyclerView.adapter = adapter
                }, { error ->
                    println(error)
                })
            )
        } ?: run {
            println("Category is null")
        }
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}