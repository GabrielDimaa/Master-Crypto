package com.ap8.appcriptomoedas.ui.bitcoin

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ap8.appcriptomoedas.AtivosAdapter
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.api.DadosAPI
import com.ap8.appcriptomoedas.api.DadosAPIHTTP
import com.ap8.appcriptomoedas.dao.Ativos
import com.ap8.appcriptomoedas.dao.AtivosDao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_bitcoin.*


class BitcoinFragment : Fragment() {

    private lateinit var bitcoinViewModel: BitcoinViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bitcoinViewModel =
                ViewModelProviders.of(this).get(BitcoinViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bitcoin, container, false)
        val textView: TextView = root.findViewById(R.id.text_btc)
        val recycle: RecyclerView = root.findViewById(R.id.recycle_btc)
        bitcoinViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val btn: FloatingActionButton = view.findViewById(R.id.buttonFloating)

        btn.setOnClickListener(View.OnClickListener {
            val it = Intent(activity, BitcoinSave::class.java)
            activity?.startActivity(it)

        })

//        carregarEstatisticas()
        updateAdapter()
        initRecyclerView()
        val teste = ""

    }

    var dado = DadosAPI()



    inner class EstatisticasTask: AsyncTask<Void, Void, DadosAPI>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): DadosAPI {
            val teste = DadosAPIHTTP.loadAPI()
            return teste
        }

        override fun onPostExecute(resultado: DadosAPI) {
            super.onPostExecute(resultado)
            atualizarEstatisticas(resultado)
        }

    }

    fun atualizarEstatisticas(resultado: DadosAPI) {
        if (resultado != null) {
            dado = resultado
        }
    }

    private var asyncTask: EstatisticasTask? = null
    fun carregarEstatisticas() {
        if (asyncTask == null) {
            if(DadosAPIHTTP.hasConnection(this.requireContext())) {
                if (asyncTask?.status != AsyncTask.Status.RUNNING) {
                    asyncTask = EstatisticasTask()
                    asyncTask?.execute()
                }
            }
        }
    }

    var listaAtivos = mutableListOf<Ativos>()

    private fun updateAdapter() {
        val comprasDao = getActivity()?.let { AtivosDao(it) }
        listaAtivos.clear()
        if (comprasDao != null) {
            listaAtivos = comprasDao.get()
        }
        if(listaAtivos.isEmpty()) {
            recycle_btc.setVisibility(View.GONE)
            recycle_btc.setVisibility(View.VISIBLE)
        } else {
            recycle_btc.setVisibility(View.VISIBLE)
        }
        recycle_btc.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView() {

        val adapter_ = AtivosAdapter(listaAtivos)
        val layout = LinearLayoutManager(getActivity())
        recycle_btc.setLayoutManager(layout)
        recycle_btc.setHasFixedSize(true)
        recycle_btc.setAdapter(adapter_)
    }

//    private fun initRecyclerView() {
//        val adapter_ = AtivosAdapter(listaAtivos)
//
//        recycle_btc.adapter = adapter_
//        val layout = GridLayoutManager(requireContext(), 2)
//        recycle_btc.layoutManager = layout
//    }
}
