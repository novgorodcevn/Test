package com.example.mynews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.mynews.HeadlinesPagerAdapter
import com.example.mynews.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


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
                0 -> "business"
                1 -> "sports"
                2 -> "health"
                3 -> "entertainment"
                4 -> "technology"
                5 -> "general"
                6 -> "science"
                else -> "science"
            }
        }.attach()
    }
}