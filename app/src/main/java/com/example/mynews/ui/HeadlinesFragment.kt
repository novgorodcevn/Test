package com.example.mynews.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mynews.AppDatabase
import com.example.mynews.DatabaseBuilder
import com.example.mynews.HeadlinesPagerAdapter
import com.example.mynews.NewsDao
import com.example.mynews.R
import com.example.mynews.adapter.HeadlinesAdapter
import com.example.mynews.data.NewsItem
import com.example.mynews.ui.headlines.HeadlinesView
import com.example.mynews.viewmodel.HeadlinesModel
import com.example.mynews.viewmodel.HeadlinesViewModel
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment


class HeadlinesFragment : BaseFragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_headlines, container, false)
        viewPager = view.findViewById(R.id.headlinesViewPager)
        tabLayout = view.findViewById(R.id.headlinesTabLayout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = HeadlinesPagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Business"
                1 -> "TRAVELING"
                2 -> "HEALTH"
                3 -> "ENTERTAINMENT"
                4 -> "TECHNOLOGY"
                5 -> "General"
                else -> "General"
            }
        }.attach()
    }
}