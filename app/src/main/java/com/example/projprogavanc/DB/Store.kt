package com.example.projprogavanc.DB

import android.content.ContentValues

/**
 * Class para item do tipo Store
 *
 * @param name-> Nome da loja
 * @param local -> localidade, endereço ou link da loja
 * @param type -> tipo da loja (Digital ou física)
 * @param id -> id da loja
 *
 */

class Store(var name: String, var local: String, var type: String, var id: Long = -1) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBStores.C_NAME, name)
        values.put(TDBStores.C_LOCAL, local)
        values.put(TDBGames.C_TYPE, type)

        return values

    }

}