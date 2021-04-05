package com.example.tz.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tz.R
import com.example.tz.sqlite.CustomAdapter
import com.example.tz.sqlite.MyDatabaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import java.util.*


class CocktailsFragment : Fragment() {

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    private var filter_button : Button? = null
    private var sort_button : Button? = null
    
    private var chip1 = false
    private var chip2 = false
    private var chip3 = false
    private var chip4 = false
    private var chip2_1 = false
    private var chip2_2 = false
    private var chip2_3 = false
    private var chip2_4 = false


    ////////////////////////////////////////////////////
    private var recyclerView: RecyclerView? = null
    private var myDB: MyDatabaseHelper? = null
    private var book_id: ArrayList<String>? = null
    private var book_author: ArrayList<String>? = null
    private var book_title: ArrayList<String>? = null
    private var book_pages: ArrayList<String>? = null
    private var customAdapter: CustomAdapter? = null
    private var empty_imageview: ImageView? = null
    private var no_data: TextView? = null
    ///////////////////////////////////////////////////
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
            //TODO: add filter sheet logic
            val bottomSheetDialog =  BottomSheetDialog(this.requireContext())
            bottomSheetDialog.setContentView(btnsheetfilter)
            val bottomSheetView = LayoutInflater.from(this.requireContext()).inflate(R.layout.sheet_filter_cocktails, root.findViewById(R.id.cocktails_filter_sheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()

            if (chip1) bottomSheetView.findViewById<Chip>(R.id.chip_1).isChecked = true
            if (chip2) bottomSheetView.findViewById<Chip>(R.id.chip_2).isChecked = true
            if (chip3) bottomSheetView.findViewById<Chip>(R.id.chip_3).isChecked = true
            if (chip4) bottomSheetView.findViewById<Chip>(R.id.chip_4).isChecked = true
            if (chip2_1) bottomSheetView.findViewById<Chip>(R.id.chip2_1).isChecked = true
            if (chip2_2) bottomSheetView.findViewById<Chip>(R.id.chip2_2).isChecked = true
            if (chip2_3) bottomSheetView.findViewById<Chip>(R.id.chip2_3).isChecked = true
            if (chip2_4) bottomSheetView.findViewById<Chip>(R.id.chip2_4).isChecked = true
            //bottomSheetView.findViewById<Chip>(R.id.chip_1).onRestoreInstanceState(1)

            bottomSheetView.findViewById<Chip>(R.id.chip_1).setOnCheckedChangeListener {
                compoundButton: CompoundButton, isChecked: Boolean ->
                //bottomSheetView.findViewById<Chip>(R.id.chip_1).onSaveInstanceState()
                chip1 = if (isChecked){
                    Toast.makeText(this.requireContext(), "you checked filter 1", Toast.LENGTH_SHORT).show()
                    true
                } else {
                    Toast.makeText(this.requireContext(), "you unchecked filter 1", Toast.LENGTH_SHORT).show()
                    false
                }
            }

            bottomSheetView.findViewById<Chip>(R.id.chip_2).setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                chip2 = if (isChecked){
                    Toast.makeText(this.requireContext(), "you checked filter 2", Toast.LENGTH_SHORT).show()
                    true
                } else {
                    Toast.makeText(this.requireContext(), "you unchecked filter 2", Toast.LENGTH_SHORT).show()
                    false
                }
            }

            bottomSheetView.findViewById<Chip>(R.id.chip_3).setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                chip3 = isChecked
            }

            bottomSheetView.findViewById<Chip>(R.id.chip_4).setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                chip4 = isChecked
            }

            bottomSheetView.findViewById<Chip>(R.id.chip2_1).setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                chip2_1 = isChecked
            }

            bottomSheetView.findViewById<Chip>(R.id.chip2_2).setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                chip2_2 = isChecked
            }

            bottomSheetView.findViewById<Chip>(R.id.chip2_3).setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                chip2_3 = isChecked
            }

            bottomSheetView.findViewById<Chip>(R.id.chip2_4).setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
                chip2_4 = isChecked
            }

            bottomSheetView.findViewById<Button>(R.id.filter_sheet_done).setOnClickListener {
                bottomSheetDialog.dismiss()
            }


        }

        //SHEET SORT
        val btnsheetsort = layoutInflater.inflate(R.layout.sheet_sort_cocktails, null)

        sort_button?.setOnClickListener{
            //TODO: add sort sheet logic
            val bottomSheetDialog =  BottomSheetDialog(this.requireContext())
            bottomSheetDialog.setContentView(btnsheetsort)
            val bottomSheetView = LayoutInflater.from(this.requireContext()).inflate(R.layout.sheet_sort_cocktails, root.findViewById(R.id.cocktails_sort_sheet))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }


        setSwipeRefreshLayout()

        myDB = MyDatabaseHelper(this@CocktailsFragment.requireContext())
        book_id = ArrayList()
        book_title = ArrayList()
        book_author = ArrayList()
        book_pages = ArrayList()

        storeDataInArrays(book_id, book_title, book_author, book_pages)


        customAdapter = CustomAdapter(this@CocktailsFragment, this.requireContext(), book_id!!, book_title!!, book_author!!,
                book_pages!!)
        recyclerView?.adapter = customAdapter
        customAdapter?.setData(book_id, book_title, book_author, book_pages)
        recyclerView?.layoutManager = LinearLayoutManager(this@CocktailsFragment.requireContext())

        return root
    }


    private fun refreshData() {
        val new_book_id : ArrayList<String> = ArrayList()
        val new_book_title : ArrayList<String> = ArrayList()
        val new_book_author : ArrayList<String> = ArrayList()
        val new_book_pages : ArrayList<String> = ArrayList()
        storeDataInArrays(new_book_id, new_book_title, new_book_author, new_book_pages)
        /*book_title?.clear()
        book_author?.clear()
        book_pages?.clear()
        storeDataInArrays()*/

        //recyclerView?.layoutManager = LinearLayoutManager(this@CocktailsFragment.requireContext())
        customAdapter?.updateData(new_book_id, new_book_title, new_book_author, new_book_pages)
    }

    private fun setSwipeRefreshLayout() {
        mSwipeRefreshLayout?.setOnRefreshListener {
            // callback when swiped from top of screen
            // make your network request again, modify recycler adapter,etc
            refreshData()
            mSwipeRefreshLayout?.isRefreshing = false // set false to dismiss the progress loader once your job is done
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun storeDataInArrays(listId : ArrayList<String>?, listTitle : ArrayList<String>?, listAuthor : ArrayList<String>?, listPages : ArrayList<String>?, ) {
        val cursor = myDB!!.readAllData()
        if (cursor!!.count == 0) {
            empty_imageview?.visibility = View.VISIBLE
            no_data?.visibility = View.VISIBLE
        } else {
            while (cursor.moveToNext()) {
                listId?.add(cursor.getString(0))
                listTitle?.add(cursor.getString(1))
                listAuthor?.add(cursor.getString(2))
                listPages?.add(cursor.getString(3))
            }
            empty_imageview?.visibility = View.GONE
            no_data?.visibility = View.GONE
        }
    }
}