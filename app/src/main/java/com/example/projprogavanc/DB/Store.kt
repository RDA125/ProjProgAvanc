package com.example.projprogavanc.DB

import android.content.ContentValues

class Store(var id: Long, var name: String, var local: String, var type: String) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBStores.C_NAME, name)
        values.put(TDBStores.C_LOCAL, local)
        values.put(TDBGames.C_TYPE, type)

        return values

    }

}