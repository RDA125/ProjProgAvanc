package com.example.projprogavanc

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

data class Game(var name: String, var type: GameType, var id: Long = -1) {

    fun toContentValues(): ContentValues{

        val values = ContentValues()
        values.put(TDBGames.C_NAME, name)
        values.put(TDBGames.C_GAMETYPE_ID, type.id)

        return values

    }

    companion object{
        fun fromCursor(cursor: Cursor): Game {

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posName = cursor.getColumnIndex(TDBGames.C_NAME)
            val posType = cursor.getColumnIndex(TDBGames.C_GAMETYPE_ID)
            val posTypeName = cursor.getColumnIndex(TDBGameTypes.C_TYPE)

            val id = cursor.getLong(posId)
            val name = cursor.getString(posName)
            val type = cursor.getLong(posType)
            val typeName = cursor.getString(posTypeName)

            val gameType = GameType(typeName, type)
            return Game(name,gameType,id)
        }

    }

}