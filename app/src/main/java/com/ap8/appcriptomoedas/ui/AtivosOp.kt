package com.ap8.appcriptomoedas.ui

import android.app.DatePickerDialog
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
        val moeda = intent.getStringExtra("moeda")
        val valorizacao = intent.getStringExtra("valorizacao")

        if(moeda != null) {
            setContentView(R.layout.activity_save)

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val openCalender = findViewById<LinearLayout>(R.id.linearcalendar)
            openCalender.setOnClickListener(View.OnClickListener {
                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year_, month_, day_ ->
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