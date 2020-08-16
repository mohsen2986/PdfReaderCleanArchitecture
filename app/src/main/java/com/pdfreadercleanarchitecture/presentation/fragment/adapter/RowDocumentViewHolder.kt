package com.pdfreadercleanarchitecture.presentation.fragment.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.core.domain.Document
import com.pdfreadercleanarchitecture.R
import com.pdfreadercleanarchitecture.presentation.util.StringUtil
import kotlinx.android.synthetic.main.item_document.view.*

class RowDocumentViewHolder(
    view: View
): RecyclerView.ViewHolder(view){
    val previewImageView: ImageView = view.ivPreview
    val titleTextView: TextView = view.tvTitle
    val sizeTextView: TextView = view.tvSize

    fun bind(document: Document, glide: RequestManager, itemClickListener: (Document) -> Unit){
        glide
            .load(document.thumbnail)
            .error(glide.load(R.drawable.preview_missing))
            .into(previewImageView)

        previewImageView.setImageResource(R.drawable.preview_missing)
        titleTextView.text = document.name
        sizeTextView.text = StringUtil.readableFileSize(document.size)
        itemView.setOnClickListener { itemClickListener.invoke(document) }
    }
}