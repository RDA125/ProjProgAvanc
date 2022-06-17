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

        db.execSQL("create table $T_NAME (${BaseColumns._ID} Integer primary key autoincrement, $C_NAME text not null, $C_ADDRESS Text not null ,$C_STORETYPE_ID Text not null, foreign key($C_STORETYPE_ID) references ${TDBStoreTypes.T_NAME} (${BaseColumns._ID}) on delete restrict)")

    }

    /**
     * Nome das colunas da tabela Stores
     */
    companion object{

        const val T_NAME = "Stores"
        const val C_NAME = "Name"
        const val C_ADDRESS = "Address"
        const val C_STORETYPE_ID = "Type"

        val ALL_COLUMNS = arrayOf(BaseColumns._ID, C_NAME, C_ADDRESS, C_STORETYPE_ID)
    }
}