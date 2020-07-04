package com.ap8.appcriptomoedas.ui.etherium

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ap8.appcriptomoedas.AtivosAdapter
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.methods.AtivosMethods
import com.ap8.appcriptomoedas.ui.AtivosOp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vicmikhailau.maskededittext.MaskedFormatter
import com.vicmikhailau.maskededittext.MaskedWatcher
import kotlinx.android.synthetic.main.fragment_bcash.*
import kotlinx.android.synthetic.main.fragment_ethereum.*
import java.text.DecimalFormat


class EtheriumFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var total: Double = 0.0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_ethereum, container, false)
        val btn: FloatingActionButton = root.findViewById(R.id.buttonFloating)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_etc)

        btn.setOnClickListener(View.OnClickListener {
            val it = Intent(activity, AtivosOp::class.java)
            it.putExtra("moeda", "ETC")
            activity?.startActivity(it)
        })

        initRecyclerView(recycle)
        return root
    }

    override fun onStart() {
        super.onStart()
        updateAdapter()
        if(listaAtivos.isEmpty()) {
            etc_msg.text = "Nenhum ativo adicionado para esta moeda, \n adicione em +"
        }
        somar()
    }

    private fun updateAdapter() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.getBymoeda("ETC")
        }
        if(listaAtivos.isEmpty()) {
            recycle_etc.visibility = View.GONE
            recycle_etc.visibility = View.VISIBLE
        } else {
            recycle_etc.visibility = View.VISIBLE
            etc_msg.text = ""
        }
        recycle_etc.adapter = AtivosAdapter(listaAtivos)
        recycle_etc.adapter?.notifyDataSetChanged()
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
        total_etc.text = "R$ ${valor}"
    }
}