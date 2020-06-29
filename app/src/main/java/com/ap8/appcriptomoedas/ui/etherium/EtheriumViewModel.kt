package com.ap8.appcriptomoedas.ui.etherium

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EtheriumViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ethereum Fragment"
    }
    val text: LiveData<String> = _text
}