package com.ap8.appcriptomoedas.ui.bitcoin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.ap8.appcriptomoedas.ui.Resumo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_bcash.*
import kotlinx.android.synthetic.main.fragment_bitcoin.*
import kotlinx.android.synthetic.main.fragment_ethereum.*
import kotlinx.android.synthetic.main.fragment_litecoin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BitcoinFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var total: Double = 0.0
    private var btcAPI: MoedaAPI? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bitcoin, container, false)
        val btnAdd: FloatingActionButton = root.findViewById(R.id.buttonFloating)
        val btnResumo: FloatingActionButton = root.findViewById(R.id.buttonFloating2)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_btc)

        btnAdd.setOnClickListener(View.OnClickListener {
            val it = Intent(activity, AtivosOp::class.java)
            it.putExtra("moeda", "BTC")
            it.putExtra("price", btcAPI?.price)
            activity?.startActivity(it)
        })

        btnResumo.setOnClickListener(View.OnClickListener {
            if (btcAPI == null) {
                Toast.makeText(activity, "Sem Conexão!", Toast.LENGTH_SHORT).show()
            } else {
                val it = Intent(activity, Resumo::class.java)
                it.putExtra("moeda", "BTC")
                it.putExtra("preco", btcAPI?.price)
                it.putExtra("maior", btcAPI?.high)
                it.putExtra("menor", btcAPI?.low)
                it.putExtra("volume", btcAPI?.vol)
                activity?.startActivity(it)
            }
        })

        initRecyclerView(recycle)

        return root
    }

    override fun onStart() {
        super.onStart()
        if(context?.let { RetrofitConfig().hasConnection(it) }!!) {
            progressbtc.visibility = View.VISIBLE
            val call: Call<MoedaAPI> = RetrofitConfig().getMoedaService().getMoeda("btc")
                call.enqueue(object: Callback<MoedaAPI> {
                    override fun onFailure(call: Call<MoedaAPI>, t: Throwable) {
                        Log.e("onFailure error", t.message)
                        progressbtc.visibility = View.GONE
                    }
                    override fun onResponse(call: Call<MoedaAPI>, response: Response<MoedaAPI>) {
                        progressbtc.visibility = View.VISIBLE
                        btcAPI = response.body()?.ticker
                        updateAdapter()
                        somar()
                        progressbtc.visibility = View.GONE
                    }
                })
        } else {
            updateAdapter()
            somar()
            progressbtc.visibility = View.GONE
        }
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
            btc_msg.text = "Nenhum ativo adicionado para esta moeda, \n adicione em +"
            recycle_btc.adapter = AtivosAdapter(listaAtivos, null)
        } else {
            recycle_btc.visibility = View.VISIBLE
            btc_msg.text = ""
            if(btcAPI == null) {
                recycle_btc.adapter = AtivosAdapter(listaAtivos, null)
            } else {
                recycle_btc.adapter = AtivosAdapter(listaAtivos.reversed(), btcAPI?.price)
            }
            recycle_btc.adapter?.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView(recycle: RecyclerView) {
        val adapter_ = AtivosAdapter(listaAtivos.reversed(), btcAPI?.price)
        val layout = LinearLayoutManager(activity)
        recycle.setHasFixedSize(true)
        recycle.adapter = adapter_
        recycle.layoutManager = layout
    }

    fun somar() {
        total = 0.0
        for(position in 0 .. listaAtivos.size - 1) {
            val ativo = listaAtivos[position]
            total += ativo.valor
        }
        val valor = DecimalFormat("#,##0.00").format(total)
        total_btc.text = "R$ ${valor}"
    }

    fun show(show: Boolean){
        if (!show){
            progressbtc.visibility = if(show) View.VISIBLE else View.GONE
        }
    }
}
