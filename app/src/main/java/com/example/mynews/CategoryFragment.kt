package com.example.mynews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.adapter.HeadlinesAdapter
import com.example.mynews.databinding.FragmentCategoryBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HeadlinesAdapter
    private var category: String? = null
    private val compositeDisposable = CompositeDisposable()
    private var isDataLoaded = false // Add this flag

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(ARG_CATEGORY)
        Log.d("CategoryFragment", "Category: $category") // Add this log
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.categoryRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HeadlinesAdapter(mutableListOf())
        recyclerView.adapter = adapter

        if (!isDataLoaded) { // Check if data is already loaded
            loadNews()
            isDataLoaded = true // Set the flag to true
        }
    }

    private fun loadNews() {
        category?.let { categoryValue ->
            Log.d("CategoryFragment", "Loading news for category: $categoryValue")
            val disposable: Disposable = RetrofitBuilder.apiService.getTopHeadlines(
                category = categoryValue,
                apiKey = "8e3d4af8b176486cad2ea977f8a2d01c"
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newsResponse ->
                    val articles = newsResponse.articles
                    Log.d("CategoryFragment", "Articles: $articles")
                    adapter.newsList.clear()
                    adapter.newsList.addAll(articles)
                    adapter.notifyDataSetChanged()
                }, { error ->
                    Log.e("CategoryFragment", "Error loading news", error)
                    Toast.makeText(context, "Error loading news", Toast.LENGTH_SHORT).show()
                })

            compositeDisposable.add(disposable)
        } ?: run {
            Log.e("CategoryFragment", "Category is null")
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
