package com.ap8.appcriptomoedas.ui.bcash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ap8.appcriptomoedas.AtivosAdapter
import com.ap8.appcriptomoedas.MainActivity
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.methods.AtivosMethods
import com.ap8.appcriptomoedas.ui.AtivosOp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_bcash.*
import kotlinx.android.synthetic.main.fragment_bitcoin.*
import kotlinx.android.synthetic.main.fragment_ethereum.*
import kotlinx.android.synthetic.main.fragment_litecoin.*
import java.text.DecimalFormat

class BcashFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var total: Double = 0.0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_bcash, container, false)
        val btn: FloatingActionButton = root.findViewById(R.id.buttonFloating)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_bch)

        btn.setOnClickListener(View.OnClickListener {
            val it = Intent(activity, AtivosOp::class.java)
            it.putExtra("moeda", "BCH")
            activity?.startActivity(it)
        })
        initRecyclerView(recycle)

        return root
    }

    override fun onStart() {
        super.onStart()
        updateAdapter()
        if(listaAtivos.isEmpty()) {
            bch_msg.text = "Nenhum ativo adicionado para esta moeda, \n adicione em +"
        }
        somar()
    }

    private fun updateAdapter() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.getBymoeda("BCH")
        }
        if(listaAtivos.isEmpty()) {
            recycle_bch.visibility = View.GONE
            recycle_bch.visibility = View.VISIBLE
        } else {
            recycle_bch.visibility = View.VISIBLE
            bch_msg.text = ""
        }
        recycle_bch.adapter = AtivosAdapter(listaAtivos)
        recycle_bch.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView(recycle: RecyclerView) {
        val adapter_ = AtivosAdapter(listaAtivos)
        val layout = LinearLayoutManager(activity)
        recycle.setHasFixedSize(true)
        recycle.adapter = adapter_
        recycle.layoutManager = layout
    }

    fun somar() {
        for(position in 0 .. listaAtivos.size - 1) {
            val ativo = listaAtivos[position]
            total += ativo.valor
        }
        val valor = DecimalFormat("#,##0.00").format(total)
        total_bch.text = "R$ ${valor}"
    }
}