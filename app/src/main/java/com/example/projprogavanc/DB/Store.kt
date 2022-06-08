package com.example.projprogavanc.DB

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

/**
 * Class para item do tipo Store
 *
 * @param name-> Nome da loja
 * @param local -> localidade, endereço ou link da loja
 * @param type -> tipo da loja (Digital ou física)
 * @param id -> id da loja
 *
 */

data class Store(var name: String, var local: String, var type: String, var id: Long = -1) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBStores.C_NAME, name)
        values.put(TDBStores.C_LOCAL, local)
        values.put(TDBGames.C_TYPE, type)

        return values

    }

    companion object{
        fun fromCursor(cursor: Cursor):Store{

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posName = cursor.getColumnIndex(TDBStores.C_NAME)
            val posLocal = cursor.getColumnIndex(TDBStores.C_LOCAL)
            val posType = cursor.getColumnIndex(TDBStores.C_TYPE)

            val id = cursor.getLong(posId)
            val name = cursor.getString(posName)
            val local = cursor.getString(posLocal)
            val type = cursor.getString(posType)

            return Store(name,local,type,id)
        }

    }
}