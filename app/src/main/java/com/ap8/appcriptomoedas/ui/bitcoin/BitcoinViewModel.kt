package com.ap8.appcriptomoedas.ui.bitcoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BitcoinViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bitcoin Fragment"
    }
    val text: LiveData<String> = _text
}