package com.example.tz.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tz.R
import com.example.tz.sqlite.CustomAdapter
import com.example.tz.sqlite.MyDatabaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*


class CocktailsFragment : Fragment() {

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    private var filter_button : Button? = null
    private var sort_button : Button? = null


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

        val root = inflater.inflate(R.layout.fragment_cocktails, container, false)

        recyclerView = root.findViewById(R.id.recyclerView)
        mSwipeRefreshLayout = root.findViewById(R.id.swipe_refresh)
        empty_imageview = root.findViewById(R.id.empty_imageview)
        no_data = root.findViewById(R.id.no_data)
        filter_button = root.findViewById(R.id.filter_cocktails_button)
        sort_button = root.findViewById(R.id.sort_cocktails_button)


        //SHEET FILTER
        val btnsheetfilter = layoutInflater.inflate(R.layout.sheet_filter_cocktails, null)

        filter_button?.setOnClickListener{
            val bottomSheetDialog =  BottomSheetDialog(this.requireContext())
            bottomSheetDialog.setContentView(btnsheetfilter)
            val bottomSheetView = LayoutInflater.from(this.requireContext()).inflate(R.layout.sheet_filter_cocktails, root.findViewById(R.id.cocktails_filter_sheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        //SHEET SORT
        val btnsheetsort = layoutInflater.inflate(R.layout.sheet_sort_cocktails, null)

        sort_button?.setOnClickListener{
            val bottomSheetDialog =  BottomSheetDialog(this.requireContext())
            bottomSheetDialog.setContentView(btnsheetsort)
            val bottomSheetView = LayoutInflater.from(this.requireContext()).inflate(R.layout.sheet_sort_cocktails, root.findViewById(R.id.cocktails_sort_sheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
        ///////////

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
        //customAdapter?.setData(book_id, book_title, book_author, book_pages)
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
            //customAdapter?.setData(book_id, book_title, book_author, book_pages)

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