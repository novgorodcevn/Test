package com.example.mynews

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HeadlinesPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        val category = when (position) {
            0 -> "business"
            1 -> "sports"
            2 -> "health"
            3 -> "entertainment"
            4 -> "technology"
            5 -> "general"
            6 -> "science"
            else -> "business"
        }
        return CategoryFragment.newInstance(category)
    }
}