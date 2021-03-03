package com.example.tz.ui.cocktails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CocktailsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "There will be cocktails"
    }
    val text: LiveData<String> = _text
}