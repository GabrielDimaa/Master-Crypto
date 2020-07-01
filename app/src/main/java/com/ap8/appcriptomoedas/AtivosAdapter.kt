package com.ap8.appcriptomoedas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.ui.AtivosOp
import kotlinx.android.synthetic.main.adapter_ativos.view.*

class AtivosAdapter(private val ativos: List<Ativos>):
    RecyclerView.Adapter<AtivosAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtivosAdapter.VH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_ativos, parent,false)

        val vHolder = VH(view)

        view.setOnClickListener(View.OnClickListener {
            val ativo = Ativos(
                vHolder.idInvisible,
                vHolder.viewMoeda.text.toString(),
                vHolder.viewQuantidade.text.toString().toDouble(),
                vHolder.viewValor.text.toString().toDouble(),
                vHolder.viewData.text.toString()
            )

            val it = Intent(parent.context, AtivosOp::class.java)
            it.putExtra("put/del", ativo)
            parent.context.startActivity(it)
        })

        return vHolder
    }

    override fun getItemCount(): Int {
        return ativos.size
    }

    override fun onBindViewHolder(holder: AtivosAdapter.VH, position: Int) {
        val ativo = ativos[position]
        holder.idInvisible = ativo.id
        holder.viewMoeda.text = ativo.moeda.toString()
        holder.viewQuantidade.text = ativo.quantidade.toString()
        holder.viewValor.text = ativo.valor.toString()
        holder.viewData.text = ativo.data.toString()
    }

    class VH(item: View): RecyclerView.ViewHolder(item) {
        var idInvisible: Int? = null
        var viewMoeda: TextView = item.viewMoeda_
        var viewQuantidade: TextView = item.viewQuantidade_
        var viewValor: TextView = item.viewValor_
        var viewData: TextView = item.viewData_
    }
}