package com.pdfreadercleanarchitecture.presentation.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.core.domain.Document
import com.pdfreadercleanarchitecture.R

class DocumentAdapter(
    private val documents: MutableList<Document> = mutableListOf() ,
    private val glide: RequestManager ,
    private val itemClickListener: (Document) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RowDocumentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_document , parent , false)
        )
    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RowDocumentViewHolder).bind(documents[position] , glide , itemClickListener)
    }
    fun update(newDocuments: List<Document>){
        documents.clear()
        documents.addAll(newDocuments)

        notifyDataSetChanged()
    }

}