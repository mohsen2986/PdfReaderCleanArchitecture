package com.pdfreadercleanarchitecture.presentation.fragment.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.Bookmark
import com.pdfreadercleanarchitecture.R
import kotlinx.android.synthetic.main.item_bookmark.view.*

class BookmarkViewHolder(
    view:View
): RecyclerView.ViewHolder(view){
    val titleTextView: TextView = view.bookmarkNameTextView

    fun bind(bookmark: Bookmark , itemClickListener: (Bookmark) -> Unit){
        titleTextView.text = itemView.resources.getString(
            R.string.page_bookmark_format,
            bookmark.page
        )
        itemView.setOnClickListener { itemClickListener.invoke(bookmark) }
    }
}