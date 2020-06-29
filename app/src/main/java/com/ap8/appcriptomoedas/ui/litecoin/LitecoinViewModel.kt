package com.ap8.appcriptomoedas.ui.litecoin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LitecoinViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is litecoin Fragment"
    }
    val text: LiveData<String> = _text
}