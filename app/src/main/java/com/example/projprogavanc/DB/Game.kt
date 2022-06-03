package com.example.projprogavanc.DB

import android.content.ContentValues

/**
 * Class para item do tipo Game
 *
 * @param name-> Nome do jogo
 * @param type -> tipo do Jogo (Digital, física, coleção, etc.)
 * @param id -> id do jogo
 *
 */

class Game(var name: String, var type: String, var id: Long = -1) {

    fun toContentValues(): ContentValues{

        val values = ContentValues()
        values.put(TDBGames.C_NAME, name)
        values.put(TDBGames.C_TYPE, type)

        return values

    }

}