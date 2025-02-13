package com.example.mynews.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.mynews.R

open class BaseFragment: Fragment() {
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
    fun goBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }
    fun openFragmentWithoutBackStack(fragment: Fragment){
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}
