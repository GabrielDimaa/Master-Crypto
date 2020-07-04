package com.ap8.appcriptomoedas.ui.bitcoin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ap8.appcriptomoedas.AtivosAdapter
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.api.MoedaAPI
import com.ap8.appcriptomoedas.api.RetrofitConfig
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.methods.AtivosMethods
import com.ap8.appcriptomoedas.ui.AtivosOp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_bcash.*
import kotlinx.android.synthetic.main.fragment_bitcoin.*
import kotlinx.android.synthetic.main.fragment_litecoin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BitcoinFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var total: Double = 0.0
    private var moedaApi: MoedaAPI? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bitcoin, container, false)
        val btn: FloatingActionButton = root.findViewById(R.id.buttonFloating)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_btc)

        val call: Call<MoedaAPI> = RetrofitConfig().getMoedaService().getMoeda("btc")
        call.enqueue(object: Callback<MoedaAPI> {
            override fun onFailure(call: Call<MoedaAPI>, t: Throwable) {
                Log.e("onFailure error", t.message)
            }

            override fun onResponse(call: Call<MoedaAPI>, response: Response<MoedaAPI>) {
                moedaApi = response.body()?.ticker
            }

        })

        btn.setOnClickListener(View.OnClickListener {
            val it = Intent(activity, AtivosOp::class.java)
            it.putExtra("moeda", "BTC")
            activity?.startActivity(it)
        })
        initRecyclerView(recycle)

        return root
    }

    override fun onStart() {
        super.onStart()
        updateAdapter()
        if(listaAtivos.isEmpty()) {
            btc_msg.text = "Nenhum ativo adicionado para esta moeda, \n adicione em +"
        }
        somar()
    }

    private fun updateAdapter() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.getBymoeda("BTC")
        }
        if(listaAtivos.isEmpty()) {
            recycle_btc.visibility = View.GONE
            recycle_btc.visibility = View.VISIBLE
        } else {
            recycle_btc.visibility = View.VISIBLE
            btc_msg.text = ""
        }
        recycle_btc.adapter = AtivosAdapter(listaAtivos)
        recycle_btc.adapter?.notifyDataSetChanged()
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
        total_btc.text = "R$ ${valor}"
    }
}
