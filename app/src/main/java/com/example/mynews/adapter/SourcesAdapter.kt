package com.example.mynews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynews.R
import com.example.mynews.data.Source

class SourcesAdapter(val sourcesList: MutableList<Source>, val onItemClick: (Source) -> Unit) :
    RecyclerView.Adapter<SourcesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sourceImageView: ImageView = itemView.findViewById(R.id.sourceImageView)
        val sourceNameTextView: TextView = itemView.findViewById(R.id.sourceNameTextView)
        val sourceDescriptionTextView: TextView = itemView.findViewById(R.id.sourceDescriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_source, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val source = sourcesList[position]

        holder.sourceNameTextView.text = source.name
        holder.sourceDescriptionTextView.text = source.description

        // Load image based on source ID
        val imageResource = when (source.name) {
            "CNBC" -> R.drawable.cnbc
            "The New York Time" -> R.drawable.newyorktime
            "CNN" -> R.drawable.cnn
            "Daily Mail" -> R.drawable.daily
            "FOX NEWS" -> R.drawable.fox
            else -> R.drawable.ic_launcher_foreground // Default image
        }

        holder.sourceImageView.setImageResource(imageResource)

        holder.itemView.setOnClickListener {
            onItemClick(source)
        }
    }

    override fun getItemCount(): Int {
        return sourcesList.size
    }
}