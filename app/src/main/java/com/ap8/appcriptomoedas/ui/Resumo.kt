package com.ap8.appcriptomoedas.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ap8.appcriptomoedas.R
import java.text.DecimalFormat
import java.util.*

class Resumo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo)

        val moeda = intent.getStringExtra("moeda")
        val preco = intent.getDoubleExtra("preco", 0.0)
        val maior = intent.getDoubleExtra("maior", 0.0)
        val menor = intent.getDoubleExtra("menor", 0.0)
        val volume = intent.getDoubleExtra("volume", 0.0)

        val price = findViewById<TextView>(R.id.view_valorAtual)
        val high = findViewById<TextView>(R.id.view_maior)
        val low = findViewById<TextView>(R.id.view_menor)
        val vol = findViewById<TextView>(R.id.view_volume)

        Locale.setDefault(Locale("pt", "BR"))
        val formatoReal = DecimalFormat("#,##0.00")
        val volume_ = DecimalFormat("#.###")
            .format(volume)
            .replace(",", ".")

        price.text = "R$ ${formatoReal.format(preco)}"
        high.text = "R$ ${formatoReal.format(maior)}"
        low.text = "R$ ${formatoReal.format(menor)}"
        vol.text = "${volume_} ${moeda}"
    }
}