package com.ap8.appcriptomoedas.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.methods.AtivosMethods
import kotlinx.android.synthetic.main.activity_save.*

class AtivosOp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val ativo = intent.getParcelableExtra<Ativos>("put/del")
        val moeda = intent.getStringExtra("moeda")

        if(ativo != null) {
            view_moeda.text = ativo.moeda.toString()
            view_quantidade.setText(ativo.quantidade.toString())
            view_valor.setText(ativo.valor.toString())
            view_data.setText(ativo.data.toString())

            btn_save.setOnClickListener(View.OnClickListener {
                val ativo_ = Ativos(
                    ativo.id,
                    ativo.moeda,
                    view_quantidade.text.toString().toDouble(),
                    view_valor.text.toString().toDouble(),
                    view_data.text.toString()
                )

                val dao = AtivosMethods(this)
                dao.update(ativo_)

                finish()
            })
        } else {
            btn_save.setOnClickListener(View.OnClickListener {
                val ativo = Ativos(
                    null,
                    moeda,
                    view_quantidade.text.toString().toDouble(),
                    view_valor.text.toString().toDouble(),
                    view_data.text.toString()
                )
                val dao = AtivosMethods(this)
                dao.insert(ativo)

                finish()
            })
        }

    }
}