package com.example.projprogavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

/**
 * Funções para manipulaçao da tabela Store
 *
 * @param db Base de Dados a utilizar
 */
class TDBStore (val db: SQLiteDatabase){

    /**
     * criação a tabela Store
     */

    fun create(){

        db.execSQL("create table $T_NAME (${BaseColumns._ID} Integer primary key autoincrement, $C_NAME text not null, $C_LOCAL Text not null ,$C_TYPE Text default ($DEFAULT) not null)")

    }

    /**
     * Nome das colunas da tabela
     */
    companion object{

        const val T_NAME = "Store"
        const val C_NAME = "Name"
        const val C_LOCAL = "Local"
        const val C_TYPE = "Type"
        const val DEFAULT = "Digital"
    }
}