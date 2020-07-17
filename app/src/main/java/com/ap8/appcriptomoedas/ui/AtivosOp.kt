package com.ap8.appcriptomoedas.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ap8.appcriptomoedas.R
import com.ap8.appcriptomoedas.methods.Ativos
import com.ap8.appcriptomoedas.methods.AtivosMethods
import kotlinx.android.synthetic.main.activity_save.*
import kotlinx.android.synthetic.main.activity_visualizar.*
import java.text.DecimalFormat
import java.util.*


class AtivosOp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ativo = intent.getParcelableExtra<Ativos>("get/del")
        val valorizacao = intent.getStringExtra("valorizacao")
        val moeda = intent.getStringExtra("moeda")
        val price = intent.getDoubleExtra("price", 0.0)

        if(moeda != null) {
            setContentView(R.layout.activity_save)

            if (price != 0.0) {
                val valor = findViewById<EditText>(R.id.view_valor)
                valor.addTextChangedListener(object : TextWatcher {
                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        aft: Int
                    ) {
                    }

                    override fun afterTextChanged(s: Editable) {
                        val valor_ = view_valor.text.toString().toDouble()
                        val quantidade = valor_ / price
                        val formatado = DecimalFormat("#.#####")
                            .format(quantidade)
                            .replace(",", ".")
                        view_quantidade.setText(formatado)
                    }
                })
            }

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val openCalender = findViewById<LinearLayout>(R.id.linearcalendar)
            openCalender.setOnClickListener(View.OnClickListener {
                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year_, month_, day_ ->
                    view_data.text = "${day_}/${month_+1}/${year_}"
                }, year, month, day)
                dpd.show()
            })

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
        } else {
            setContentView(R.layout.activity_visualizar)
            val valor = DecimalFormat("#,##0.00").format(ativo?.valor)

            view_quantidade_.text = "R$ ${valor}"
            view_valor_.text = "${ativo?.moeda} ${ativo?.quantidade}"
            view_data_.text = ativo?.data.toString()
            view_valorizacao_.text = valorizacao

            btn_remove.setOnClickListener(View.OnClickListener {
                val dao = AtivosMethods(this)
                ativo.id?.let { it1 -> dao.remove(it1) }

                finish()
            })
        }
    }
}