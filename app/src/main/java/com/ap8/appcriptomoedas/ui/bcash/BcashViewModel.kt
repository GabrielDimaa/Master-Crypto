package com.ap8.appcriptomoedas.ui.bcash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BcashViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bcash Fragment"
    }
    val text: LiveData<String> = _text
}