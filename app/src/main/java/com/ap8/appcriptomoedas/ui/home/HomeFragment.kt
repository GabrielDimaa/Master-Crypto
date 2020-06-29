package com.ap8.appcriptomoedas.ui.home

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ap8.appcriptomoedas.MainActivity
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.api.DadosAPI
import com.ap8.appcriptomoedas.api.DadosAPIHTTP
import com.ap8.appcriptomoedas.api.getDadosAPI

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    var teste = MainActivity()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        val textView2: TextView = root.findViewById(R.id.textView2)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }
}
