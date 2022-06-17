package com.example.projprogavanc.DB

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

/**
 * Class para item do tipo Store
 *
 * @param name-> Nome da loja
 * @param address -> localidade, endereço ou link da loja
 * @param type -> tipo da loja (Digital ou física)
 * @param id -> id da loja
 *
 */

data class Store(var name: String, var address: String, var type: Long, var id: Long = -1) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBStores.C_NAME, name)
        values.put(TDBStores.C_ADDRESS, address)
        values.put(TDBGames.C_GAMETYPE_ID, type)

        return values

    }

    companion object{
        fun fromCursor(cursor: Cursor):Store{


            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posName = cursor.getColumnIndex(TDBStores.C_NAME)
            val posAddress = cursor.getColumnIndex(TDBStores.C_ADDRESS)
            val posType = cursor.getColumnIndex(TDBStores.C_STORETYPE_ID)

            val id = cursor.getLong(posId)
            val name = cursor.getString(posName)
            val address = cursor.getString(posAddress)
            val type = cursor.getLong(posType)

            return Store(name,address,type,id)
        }

    }
}