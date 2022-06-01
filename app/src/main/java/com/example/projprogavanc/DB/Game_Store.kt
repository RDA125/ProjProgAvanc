package com.example.projprogavanc.DB

import android.content.ContentValues

class Game_Store(var preco: Double, var id: Long=-1) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBGame_Store.C_PRECO, preco)

        return values

    }
}