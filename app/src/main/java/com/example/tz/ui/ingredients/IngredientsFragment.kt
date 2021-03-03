package com.example.tz.ui.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tz.R

class IngredientsFragment : Fragment() {

    private lateinit var ingredientsViewModel: IngredientsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ingredientsViewModel =
                ViewModelProvider(this).get(IngredientsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ingredients, container, false)
        val textView: TextView = root.findViewById(R.id.text_ingredients)
        ingredientsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}