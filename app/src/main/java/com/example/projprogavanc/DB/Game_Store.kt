package com.example.projprogavanc.DB

import android.content.ContentValues

class Game_Store(var preco: Double,var game_id :Long, var store_id :Long ,var id: Long=-1) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBGame_Store.C_PRECO, preco)
        values.put(TDBGame_Store.C_GAME_ID, game_id)
        values.put(TDBGame_Store.C_STORE_ID, store_id)

        return values

    }
}