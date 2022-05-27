package com.example.projprogavanc.DB

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

/**
 * Funções para manipulaçao da tabela Store
 *
 * @param db Base de Dados a utilizar
 */
class TDBStores (db: SQLiteDatabase) : TDB(db, T_NAME){

    /**
     * criação a tabela Stores
     */

    override fun create(){

        db.execSQL("create table $T_NAME (${BaseColumns._ID} Integer primary key autoincrement, $C_NAME text not null, $C_LOCAL Text not null ,$C_TYPE Text default ('$DEFAULT') not null)")

    }

    /**
     * Nome das colunas da tabela Store
     */
    companion object{

        const val T_NAME = "Stores"
        const val C_NAME = "Name"
        const val C_LOCAL = "Local"
        const val C_TYPE = "Type"
        const val DEFAULT = "Digital"
    }
}