package com.ap8.appcriptomoedas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ap8.appcriptomoedas.dao.Ativos
import kotlinx.android.synthetic.main.adapter_ativos.view.*

class AtivosAdapter(private val ativos: List<Ativos>):
    RecyclerView.Adapter<AtivosAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtivosAdapter.VH {
        val vh = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fragment_bitcoin, parent,false)

        val vh_ = VH(vh)

//        vh_.itemView.setOnClickListener{
//            val produto= compras[vh.adapterPosition]
//            val intencao = Intent(parent.context, UpdateActivity::class.java)
//            intencao.putExtra("compra", produto)
//            parent.context.startActivity(intencao)
//        }

        return vh_
    }

    override fun getItemCount(): Int {
        return ativos.size
    }

    override fun onBindViewHolder(holder: AtivosAdapter.VH, position: Int) {
        val ativo = ativos[position]
        holder.viewMoeda?.text = ativo.moeda
        holder.viewQuantidade?.text = ativo.quantidade.toString()
        holder.viewValor?.text = ativo.valor.toString()
        holder.viewData?.text = ativo.data
    }

    class VH(item: View): RecyclerView.ViewHolder(item) {
        var viewMoeda: TextView? = item.viewMoeda_
        var viewQuantidade: TextView? = item.viewQuantidade_
        var viewValor: TextView? = item.viewValor_
        var viewData: TextView? = item.viewData_
    }
}