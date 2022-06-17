package com.example.projprogavanc.DB

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

/**
 * Funções para manipulaçao da tabela Store Types
 *
 * @param db Base de Dados a utilizar
 */

class TDBStoreTypes(db: SQLiteDatabase) : TDB(db, T_NAME) {


    /**
     * criação da tabela Types
     */

    override fun create(){

        db.execSQL("create table $T_NAME (${BaseColumns._ID} Integer primary key autoincrement, $C_TYPE Text not null)")

    }

    /**
     * Nome das colunas da tabela Store Types
     */
    companion object{

        const val T_NAME = "Types"
        const val C_TYPE = "Type"

        val ALL_COLUMNS = arrayOf(BaseColumns._ID, C_TYPE)
    }
}