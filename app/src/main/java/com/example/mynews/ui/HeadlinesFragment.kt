package com.example.mynews.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mynews.CategoryFragment
import com.example.mynews.databinding.FragmentHeadlinesBinding
import com.google.android.material.tabs.TabLayoutMediator


class HeadlinesFragment : BaseFragment() {
    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!
    private val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.headlinesViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.headlinesTabLayout, binding.headlinesViewPager) { tab, position ->
            tab.text = categories[position]
        }.attach()
    }
    private inner class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount(): Int {
            return categories.size
        }

        override fun createFragment(position: Int): Fragment {
            Log.d("HeadlinesFragment", "Creating fragment for category: ${categories[position]}")  // Add this log
            return CategoryFragment.newInstance(categories[position])
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}