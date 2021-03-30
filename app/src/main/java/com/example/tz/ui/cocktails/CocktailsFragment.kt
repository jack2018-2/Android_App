package com.example.tz.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
    var recyclerView: RecyclerView? = null

    var myDB: MyDatabaseHelper? = null
    var book_id: ArrayList<String>? = null
    var book_author: ArrayList<String>? = null
    var book_title: ArrayList<String>? = null
    var book_pages: ArrayList<String>? = null
    var customAdapter: CustomAdapter? = null
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
        mSwipeRefreshLayout = root.findViewById(R.id.swipe_refresh);

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

    public fun setSwipeRefreshLayout() {
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
        if (cursor!!.count == 0) {} else {
            while (cursor.moveToNext()) {
                book_id!!.add(cursor.getString(0))
                book_title!!.add(cursor.getString(1))
                book_author!!.add(cursor.getString(2))
                book_pages!!.add(cursor.getString(3))
            }
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