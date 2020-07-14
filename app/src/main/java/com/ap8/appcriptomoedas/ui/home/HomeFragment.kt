package com.ap8.appcriptomoedas.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.methods.AtivosMethods
import kotlinx.android.synthetic.main.fragment_home.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import lecho.lib.hellocharts.view.PieChartView
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
        val btnBTC = root.findViewById<ConstraintLayout>(R.id.constraintLayoutBtc)
        val btnETH = root.findViewById<ConstraintLayout>(R.id.constraintLayoutEth)
        val btnLTC = root.findViewById<ConstraintLayout>(R.id.constraintLayoutLtc)
        val btnBCH = root.findViewById<ConstraintLayout>(R.id.constraintLayoutBhc)

        btnBTC.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_btc, null))
        btnETH.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_eth, null))
        btnLTC.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_ltc, null))
        btnBCH.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_bhc, null))

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

        pieChart(totalBTC, totalETH, totalLTC, totalBCH)

        total_home.text = "R$ ${formatar(total_)}"
        btc_total.text = "R$ ${formatar(totalBTC)}"
        eth_total.text = "R$ ${formatar(totalETH)}"
        ltc_total.text = "R$ ${formatar(totalLTC)}"
        bch_total.text = "R$ ${formatar(totalBCH)}"
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

    fun somar(lista: MutableList<Ativos>): Double {
        total = 0.0
        for(position in 0 .. lista.size - 1) {
            val ativo = lista[position]
            total += ativo.valor
        }
        val valor = total
        return valor
    }

    fun formatar(valor: Double): String {
        return DecimalFormat("#,##0.00").format(valor)
    }

    fun pieChart(
        totalBTC: Double,
        totalETH: Double,
        totalLTC: Double,
        totalBCH: Double
    ) {
        if(totalBTC == 0.0 && totalETH == 0.0 && totalLTC == 0.0 && totalBCH == 0.0) {
            chart.visibility = View.GONE
        } else {
            val pieData: MutableList<SliceValue> = ArrayList()
            pieData.add(SliceValue(totalBTC.toFloat(), Color.parseColor("#FFFF8600")))
            pieData.add(SliceValue(totalETH.toFloat(), Color.parseColor("#FF666666")))
            pieData.add(SliceValue(totalLTC.toFloat(), Color.parseColor("#FF345D9D")))
            pieData.add(SliceValue(totalBCH.toFloat(), Color.parseColor("#2ECC71")))
            val pieChartData = PieChartData(pieData)
            pieChartData.setHasCenterCircle(true)
            chart.pieChartData = pieChartData
        }
    }
}
