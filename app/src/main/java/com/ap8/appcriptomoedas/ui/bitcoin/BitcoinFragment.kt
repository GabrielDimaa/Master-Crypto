package com.ap8.appcriptomoedas.ui.bitcoin

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
import kotlinx.android.synthetic.main.fragment_bitcoin.*

class BitcoinFragment : Fragment() {

    var listaAtivos = mutableListOf<Ativos>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_bitcoin, container, false)
        val btn: FloatingActionButton = root.findViewById(R.id.buttonFloating)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_btc)

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

    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        updateAdapter()
//    var dado = DadosAPI()
//
//
//
//    inner class EstatisticasTask: AsyncTask<Void, Void, DadosAPI>() {
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//        override fun doInBackground(vararg params: Void?): DadosAPI {
//            val teste = DadosAPIHTTP.loadAPI()
//            return teste
//        }
//
//        override fun onPostExecute(resultado: DadosAPI) {
//            super.onPostExecute(resultado)
//            atualizarEstatisticas(resultado)
//        }
//
//    }
//
//    fun atualizarEstatisticas(resultado: DadosAPI) {
//        if (resultado != null) {
//            dado = resultado
//        }
//    }
//
//    private var asyncTask: EstatisticasTask? = null
//    fun carregarEstatisticas() {
//        if (asyncTask == null) {
//            if(DadosAPIHTTP.hasConnection(this.requireContext())) {
//                if (asyncTask?.status != AsyncTask.Status.RUNNING) {
//                    asyncTask = EstatisticasTask()
//                    asyncTask?.execute()
//                }
//            }
//        }




//    }


//    }
}
