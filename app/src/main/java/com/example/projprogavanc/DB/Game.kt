package com.example.projprogavanc.DB

import android.content.ContentValues

class Game(var id: Long, var name: String, var type: String) {

    fun toContentValues(): ContentValues{

        val values = ContentValues()
        values.put(TDBGames.C_NAME, name)
        values.put(TDBGames.C_TYPE, type)

        return values

    }

}