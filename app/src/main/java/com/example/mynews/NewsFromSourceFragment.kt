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
import com.example.mynews.databinding.FragmentNewsFromSourceBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class NewsFromSourceFragment : Fragment() {

    private var _binding: FragmentNewsFromSourceBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HeadlinesAdapter
    private var sourceId: String? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sourceId = arguments?.getString(ARG_SOURCE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFromSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.newsFromSourceRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HeadlinesAdapter(mutableListOf())
        recyclerView.adapter = adapter

        loadNewsFromSource()
    }

    private fun loadNewsFromSource() {
        sourceId?.let { source ->
            val disposable: Disposable = RetrofitBuilder.apiService.getTopHeadlinesBySource(
                source = source,
                apiKey = "8e3d4af8b176486cad2ea977f8a2d01c"
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ newsResponse ->
                    val articles = newsResponse.articles
                    adapter.newsList.clear()
                    adapter.newsList.addAll(articles)
                    adapter.notifyDataSetChanged()
                }, { error ->
                    Log.e("NewsFromSourceFragment", "Error loading news from source", error)
                    Toast.makeText(context, "Error loading news from source", Toast.LENGTH_SHORT).show()
                })

            compositeDisposable.add(disposable)
        } ?: run {
            Log.e("NewsFromSourceFragment", "Source ID is null")
            Toast.makeText(context, "Source ID is null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }

    companion object {
        private const val ARG_SOURCE_ID = "source_id"

        fun newInstance(sourceId: String) =
            NewsFromSourceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SOURCE_ID, sourceId)
                }
            }
    }
}