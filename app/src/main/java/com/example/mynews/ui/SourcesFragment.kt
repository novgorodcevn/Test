package com.example.mynews.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.NewsFromSourceFragment
import com.example.mynews.R
import com.example.mynews.RetrofitBuilder
import com.example.mynews.adapter.SourcesAdapter
import com.example.mynews.data.Source
import com.example.mynews.databinding.FragmentSourcesBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class SourcesFragment : Fragment() {

    private var _binding:FragmentSourcesBinding?=null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SourcesAdapter
    private val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.sourcesRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SourcesAdapter(mutableListOf()) { source ->
            openNewsFromSource(source)
        }
        recyclerView.adapter = adapter

        loadSources()
    }
   private fun loadSources() {
       val disposable:Disposable = RetrofitBuilder.apiService.getSources(apiKey = "8e3d4af8b176486cad2ea977f8a2d01c" )
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({ sourcesResponse ->
               val sources = sourcesResponse.sources
               adapter.sourcesList.clear()
               adapter.sourcesList.addAll(sources)
               adapter.notifyDataSetChanged()
           }, { error ->
               Log.e("SourcesFragment", "Error loading sources", error)
               Toast.makeText(context, "Error loading sources", Toast.LENGTH_SHORT).show()
           })

       compositeDisposable.add(disposable)
   }

   private fun openNewsFromSource(source: Source) {
        val fragment = source.id?.let { NewsFromSourceFragment.newInstance(it) }
       if (fragment != null) {
           parentFragmentManager.beginTransaction()
               .replace(R.id.nav_host_fragment, fragment)
               .addToBackStack(null)
               .commit()
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        _binding = null
    }
}
