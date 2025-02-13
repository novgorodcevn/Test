package com.example.mynews

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mynews.data.NewsItem
import com.example.mynews.data.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mynews.ui.HeadlinesFragment
import com.example.mynews.ui.SavedFragment
import com.example.mynews.ui.SourcesFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        openFragmentWithoutBackStack(HeadlinesFragment())
        bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId){
                R.id.headlinesFragment -> openFragmentWithoutBackStack(HeadlinesFragment())
                R.id.savedFragment -> openFragmentWithoutBackStack(SavedFragment())
                R.id.sourcesFragment -> openFragmentWithoutBackStack(SourcesFragment())
            }
            true
        }
    }
    fun openFragmentWithoutBackStack(fragment: androidx.fragment.app.Fragment){
        val transaction: androidx.fragment.app.FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}