package com.ap8.appcriptomoedas.ui.bitcoin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.dao.Ativos
import com.ap8.appcriptomoedas.dao.AtivosDao
import kotlinx.android.synthetic.main.activity_save.*

class BitcoinSave : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)



        btn_save.setOnClickListener(View.OnClickListener {
            val ativo = Ativos(
                null,
                "bitcoin",
                view_quantidade.text.toString().toDouble(),
                view_valor.text.toString().toDouble(),
                "12154"
            )
            val dao = AtivosDao(this)
            dao.insert(ativo)

            onBackPressed()
        })
    }

}