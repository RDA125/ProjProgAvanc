package com.example.projprogavanc.DB

import android.content.ContentValues

/**
 * Class para item do tipo Game_Store
 *
 * @param preco -> Preço do jogo dependendo da loja
 * @param game_id -> Id do jogo da tabela Games
 * @param store_id -> Id da loja da tabela Stores
 *
 */

class Game_Store(var preco: Double,var game_id :Long, var store_id :Long) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBGame_Store.C_PRECO, preco)
        values.put(TDBGame_Store.C_GAME_ID, game_id)
        values.put(TDBGame_Store.C_STORE_ID, store_id)

        return values

    }
}