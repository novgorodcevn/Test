package com.example.mynews.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.R

class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): ProgressViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_progress, parent, false)
            return ProgressViewHolder(view)
        }
    }
}
