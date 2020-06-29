package com.ap8.appcriptomoedas.ui.litecoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ap8.appcriptomoedas.R

class LitecoinFragment : Fragment() {

    private lateinit var litecoinViewModel: LitecoinViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        litecoinViewModel =
            ViewModelProviders.of(this).get(LitecoinViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_litecoin, container, false)
        val textView: TextView = root.findViewById(R.id.text_ltc)
        litecoinViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}