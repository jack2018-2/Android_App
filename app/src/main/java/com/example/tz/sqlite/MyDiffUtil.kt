package com.example.tz.sqlite

import androidx.recyclerview.widget.DiffUtil
import java.util.ArrayList

class MyDiffUtil(
        private val oldList_id: ArrayList<String>?,
        private val newList_id: ArrayList<String>?,
        private val oldList_title: ArrayList<String>?,
        private val newList_title: ArrayList<String>?,
        private val oldList_author: ArrayList<String>?,
        private val newList_author: ArrayList<String>?,
        private val oldList_pages: ArrayList<String>?,
        private val newList_pages: ArrayList<String>?
        ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList_id?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList_id?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList_id?.get(oldItemPosition) == newList_id?.get(oldItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList_id?.get(oldItemPosition) != newList_id?.get(newItemPosition) -> false
            oldList_title?.get(oldItemPosition) != newList_title?.get(newItemPosition) -> false
            oldList_author?.get(oldItemPosition) != newList_author?.get(newItemPosition) -> false
            oldList_pages?.get(oldItemPosition) != newList_pages?.get(newItemPosition) -> false
            else -> true
        }
    }
}