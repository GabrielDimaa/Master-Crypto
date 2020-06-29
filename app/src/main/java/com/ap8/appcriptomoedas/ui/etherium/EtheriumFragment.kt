package com.ap8.appcriptomoedas.ui.etherium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ap8.appcriptomoedas.R

class EtheriumFragment : Fragment() {

    private lateinit var ethereumViewModel: EtheriumViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ethereumViewModel =
                ViewModelProviders.of(this).get(EtheriumViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ethereum, container, false)
        val textView: TextView = root.findViewById(R.id.text_eth)
        ethereumViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
