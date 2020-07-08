package com.ap8.appcriptomoedas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.methods.AtivosMethods
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.DecimalFormat


class HomeFragment : Fragment() {

    private var listaAtivos = mutableListOf<Ativos>()
    private var listaAtivosBTC = getListaByMoeda("BTC")
    private var listaAtivosETH = getListaByMoeda("ETH")
    private var listaAtivosLTC = getListaByMoeda("LTC")
    private var listaAtivosBCH = getListaByMoeda("BCH")
    private var total: Double = 0.0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onStart() {
        super.onStart()
        listaAtivosBTC = getListaByMoeda("BTC")
        listaAtivosETH = getListaByMoeda("ETH")
        listaAtivosLTC = getListaByMoeda("LTC")
        listaAtivosBCH = getListaByMoeda("BCH")

        getListaDB()
        val total_ = somar(listaAtivos)
        val totalBTC = somar(listaAtivosBTC)
        val totalETH = somar(listaAtivosETH)
        val totalLTC = somar(listaAtivosLTC)
        val totalBCH = somar(listaAtivosBCH)

        total_home.text = "R$ ${total_}"
        btc_total.text = "R$ ${totalBTC}"
        eth_total.text = "R$ ${totalETH}"
        ltc_total.text = "R$ ${totalLTC}"
        bch_total.text = "R$ ${totalBCH}"
    }

    fun getListaByMoeda(moeda: String): MutableList<Ativos> {
        var lista = mutableListOf<Ativos>()
        val methods = activity?.let { AtivosMethods(it) }
        if (methods != null) {
            lista = methods.getBymoeda(moeda)
        }
        return lista
    }

    fun getListaDB() {
        val methods = activity?.let { AtivosMethods(it) }
        listaAtivos.clear()
        if (methods != null) {
            listaAtivos = methods.get()
        }
    }

    fun somar(lista: MutableList<Ativos>): String {
        total = 0.0
        for(position in 0 .. lista.size - 1) {
            val ativo = lista[position]
            total += ativo.valor
        }
        val valor = DecimalFormat("#,##0.00").format(total)
        return valor
    }
}
