package com.example.tz.sqlite

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tz.R
import java.util.*


class CustomAdapter internal constructor(private val activity: Fragment, private val context: Context, private var book_id: ArrayList<String>?, private var book_title: ArrayList<String>?, private var book_author: ArrayList<String>?,
                                         private var book_pages: ArrayList<String>?) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.book_id_txt.text = (book_id?.get(position))
        holder.book_title_txt.text = (book_title?.get(position))
        holder.book_author_txt.text = (book_author?.get(position))
        holder.book_pages_txt.text = (book_pages?.get(position))
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("id", book_id?.get(position))
            intent.putExtra("title", book_title?.get(position))
            intent.putExtra("author", book_author?.get(position))
            intent.putExtra("pages", book_pages?.get(position))
            activity.startActivityForResult(intent, 1)
        }
    }

    override fun getItemCount(): Int {
        return book_id?.size ?: 0
    }

    //DIFF UTIL
    fun setData(new_list_id: ArrayList<String>?,
                new_list_title: ArrayList<String>?,
                new_list_author: ArrayList<String>?,
                new_list_pages: ArrayList<String>?
    ) {
        val diffUtil = MyDiffUtil(book_id, new_list_id,
                book_title, new_list_title,
                book_author, new_list_author,
                book_pages, new_list_pages)
        //val diffResults = DiffUtil.calculateDiff(diffUtil)
        val diffResults : DiffUtil.DiffResult = DiffUtil.calculateDiff(MyDiffUtil(
                book_id, new_list_id,
                book_title, new_list_title,
                book_author, new_list_author,
                book_pages, new_list_pages))
        book_id = new_list_id
        book_title = new_list_title
        book_author = new_list_author
        book_pages = new_list_pages
        diffResults.dispatchUpdatesTo(this)
    }

    fun updateData(new_list_id: ArrayList<String>?,
                new_list_title: ArrayList<String>?,
                new_list_author: ArrayList<String>?,
                new_list_pages: ArrayList<String>?
    ) {
        val diffResults : DiffUtil.DiffResult = DiffUtil.calculateDiff(MyDiffUtil(
                book_id, new_list_id,
                book_title, new_list_title,
                book_author, new_list_author,
                book_pages, new_list_pages))
        book_id = new_list_id
        book_title = new_list_title
        book_author = new_list_author
        book_pages = new_list_pages
        diffResults.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var book_id_txt: TextView = itemView.findViewById(R.id.book_id_txt)
        var book_title_txt: TextView = itemView.findViewById(R.id.book_title_txt)
        var book_author_txt: TextView = itemView.findViewById(R.id.book_author_txt)
        var book_pages_txt: TextView = itemView.findViewById(R.id.book_pages_txt)
        var mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)

    }
}