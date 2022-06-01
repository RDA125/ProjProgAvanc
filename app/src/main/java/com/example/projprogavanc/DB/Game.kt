package com.example.projprogavanc.DB

import android.content.ContentValues

class Game(var name: String, var type: String, var id: Long = -1) {

    fun toContentValues(): ContentValues{

        val values = ContentValues()
        values.put(TDBGames.C_NAME, name)
        values.put(TDBGames.C_TYPE, type)

        return values

    }

}