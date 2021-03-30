package com.example.tz.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tz.R

class CocktailsFragment : Fragment() {

    private lateinit var cocktailsViewModel: CocktailsViewModel

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