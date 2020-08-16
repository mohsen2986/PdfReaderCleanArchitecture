package com.pdfreadercleanarchitecture.presentation.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.Bookmark
import com.pdfreadercleanarchitecture.R

class BookmarkAdapter(
    private val bookmarks: MutableList<Bookmark> = mutableListOf() ,
    private val itemClickListener: (Bookmark) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BookmarkViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark , parent , false))
    }

    override fun getItemCount(): Int = bookmarks.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BookmarkViewHolder).bind(bookmarks[position] , itemClickListener)
    }

    fun update(newBookmarks: List<Bookmark>){
        bookmarks.clear()
        bookmarks.addAll(newBookmarks)

        notifyDataSetChanged()
    }

}