package com.ap8.appcriptomoedas.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.ap8.appcriptomoedas.db.*

class AtivosDao(context: Context) {
    var db_ = DbHelper(context)

    fun insert(ativos: Ativos): String {
        val db = db_.writableDatabase
        val values = ContentValues()
        values.put(MOEDA, ativos.moeda)
        values.put(QUANTIDADE, ativos.quantidade)
        values.put(VALOR, ativos.valor)
        values.put(DATA, ativos.data)

        val resp = db.insert(TABLE_ATIVO, null, values)
        val msg = if(resp != -1L) {
            "Inserido com sucesso"
        } else {
            "Erro ao inserir"
        }
        db.close()

        return msg
    }

    // fun update

    fun remove(ativos: Ativos): Int {
        val db = db_.writableDatabase
        return db.delete(TABLE_ATIVO, "ID = ?", arrayOf(ativos.id.toString()))
    }

    fun get(): ArrayList<Ativos> {
        val db = db_.writableDatabase
        val sql = "SELECT * FROM ${TABLE_ATIVO}"
        val cursor = db.rawQuery(sql, null)
        val lista = ArrayList<Ativos>()

        while(cursor.moveToNext()) {
            val ativo = ativoFromCursor(cursor)
            lista.add(ativo)
        }

        cursor.close()
        db.close()
        //Log.v("LOG", "Get ${compras.size}")
        return lista
    }

    fun getByProduto(moeda: String): ArrayList<Ativos> {
        val db = db_.writableDatabase
        val sql = "SELECT * FROM ${TABLE_ATIVO} WHERE ${MOEDA} LIKE '%$moeda%'"
        val cursor = db.rawQuery(sql ,null)
        val lista = ArrayList<Ativos>()

        while(cursor.moveToNext()) {
            val moeda_ = ativoFromCursor(cursor)
            lista.add(moeda_)
        }

        cursor.close()
        db.close()
        return lista
    }

    private fun ativoFromCursor(cursor: Cursor): Ativos {
        val id = cursor.getInt(cursor.getColumnIndex(ID))
        val moeda = cursor.getString(cursor.getColumnIndex(MOEDA))
        val quantidade = cursor.getDouble(cursor.getColumnIndex(QUANTIDADE))
        val valor = cursor.getDouble(cursor.getColumnIndex(VALOR))
        val data = cursor.getString(cursor.getColumnIndex(DATA))
        return Ativos(id, moeda, quantidade, valor, data)
    }
}