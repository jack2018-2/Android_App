package com.example.tz.ui.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoppingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "There will be shopping list"
    }
    val text: LiveData<String> = _text
}