package com.example.projprogavanc.DB

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

/**
 * Class para item do tipo Game
 *
 * @param name-> Nome do jogo
 * @param type -> tipo do Jogo (Digital, física, coleção, etc.)
 * @param id -> id do jogo
 *
 */

data class Game(var name: String, var type: String, var id: Long = -1) {

    fun toContentValues(): ContentValues{

        val values = ContentValues()
        values.put(TDBGames.C_NAME, name)
        values.put(TDBGames.C_TYPE, type)

        return values

    }

    companion object{
        fun fromCursor(cursor: Cursor):Game{

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posName = cursor.getColumnIndex(TDBGames.C_NAME)
            val posType = cursor.getColumnIndex(TDBGames.C_TYPE)

            val id = cursor.getLong(posId)
            val name = cursor.getString(posName)
            val type = cursor.getString(posType)

            return Game(name,type,id)
        }

    }

}