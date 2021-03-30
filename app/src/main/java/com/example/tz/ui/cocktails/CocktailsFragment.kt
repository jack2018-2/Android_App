package com.example.tz.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tz.R
import com.example.tz.sqlite.CustomAdapter
import com.example.tz.sqlite.MyDatabaseHelper
import java.util.*


class CocktailsFragment : Fragment() {

    private lateinit var cocktailsViewModel: CocktailsViewModel

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private var recyclerView: RecyclerView? = null

    private var myDB: MyDatabaseHelper? = null
    private var book_id: ArrayList<String>? = null
    private var book_author: ArrayList<String>? = null
    private var book_title: ArrayList<String>? = null
    private var book_pages: ArrayList<String>? = null
    private var customAdapter: CustomAdapter? = null
    private var empty_imageview: ImageView? = null
    private var no_data: TextView? = null
    ////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cocktailsViewModel =
                ViewModelProvider(this).get(CocktailsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_cocktails, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)
        mSwipeRefreshLayout = root.findViewById(R.id.swipe_refresh)
        empty_imageview = root.findViewById(R.id.empty_imageview)
        no_data = root.findViewById(R.id.no_data)

        setSwipeRefreshLayout()

        myDB = MyDatabaseHelper(this@CocktailsFragment.requireContext())
        book_id = ArrayList()
        book_title = ArrayList()
        book_author = ArrayList()
        book_pages = ArrayList()

        storeDataInArrays()

        customAdapter = CustomAdapter(this@CocktailsFragment, this.requireContext(), book_id!!, book_title!!, book_author!!,
                book_pages!!)
        recyclerView?.adapter = customAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this@CocktailsFragment.requireContext())

        return root
    }

    private fun setSwipeRefreshLayout() {
        mSwipeRefreshLayout?.setOnRefreshListener {
            // callback when swiped from top of screen
            // make your network request again, modify recycler adapter,etc
            book_id?.clear()
            book_title?.clear()
            book_author?.clear()
            book_pages?.clear()
            storeDataInArrays()
            recyclerView?.layoutManager = LinearLayoutManager(this@CocktailsFragment.requireContext())

            mSwipeRefreshLayout?.isRefreshing = false // set false to dismiss the progress loader once your job is done
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun storeDataInArrays() {
        val cursor = myDB!!.readAllData()
        if (cursor!!.count == 0) {
            empty_imageview?.visibility = View.VISIBLE
            no_data?.visibility = View.VISIBLE
        } else {
            while (cursor.moveToNext()) {
                book_id!!.add(cursor.getString(0))
                book_title!!.add(cursor.getString(1))
                book_author!!.add(cursor.getString(2))
                book_pages!!.add(cursor.getString(3))
            }
            empty_imageview?.visibility = View.GONE
            no_data?.visibility = View.GONE
        }
    }

    /*override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cocktailsViewModel =
                ViewModelProvider(this).get(CocktailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cocktails, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        cocktailsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }*/
}