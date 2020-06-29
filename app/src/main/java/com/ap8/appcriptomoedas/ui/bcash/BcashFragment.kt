package com.ap8.appcriptomoedas.ui.bcash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ap8.appcriptomoedas.R

class BcashFragment : Fragment() {

    private lateinit var bcashViewModel: BcashViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bcashViewModel =
                ViewModelProviders.of(this).get(BcashViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bcash, container, false)
        val textView: TextView = root.findViewById(R.id.text_bhc)
        bcashViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
