package com.example.projprogavanc.DB

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

/**
 * Class para item do tipo Game_Store
 *
 * @param preco -> Preço do jogo dependendo da loja
 * @param game_id -> Id do jogo da tabela Games
 * @param store_id -> Id da loja da tabela Stores
 *
 */

data class Game_Store(var preco: Double,var game_id :Long, var store_id :Long, var id : Long = -1) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBGame_Store.C_PRECO, preco)
        values.put(TDBGame_Store.C_GAME_ID, game_id)
        values.put(TDBGame_Store.C_STORE_ID, store_id)

        return values

    }

    companion object{
        fun fromCursor(cursor: Cursor):Game_Store{

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posPrice = cursor.getColumnIndex(TDBGame_Store.C_PRECO)
            val posGameId = cursor.getColumnIndex(TDBGame_Store.C_GAME_ID)
            val posStoreId = cursor.getColumnIndex(TDBGame_Store.C_STORE_ID)

            val id = cursor.getLong(posId)
            val price = cursor.getDouble(posPrice)
            val gameId = cursor.getLong(posGameId)
            val storeId = cursor.getLong(posStoreId)

            return Game_Store(price,gameId,storeId,id)
        }

    }
}