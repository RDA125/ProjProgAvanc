package com.example.projprogavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
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

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {

        val queryBuilder = SQLiteQueryBuilder()

        queryBuilder.tables = "$T_NAME inner join ${TDBStoreTypes.T_NAME} on $C_STORETYPE_ID = ${TDBStoreTypes.C_ID} "


        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    /**
     * Nome das colunas da tabela Stores
     */
    companion object{

        const val T_NAME = "Stores"
        const val C_ID = "$T_NAME.${BaseColumns._ID}"
        const val C_NAME = "StoreName"
        const val C_ADDRESS = "Address"
        const val C_STORETYPE_ID = "StoreType"

        val ALL_COLUMNS = arrayOf(C_ID, C_NAME, C_ADDRESS, C_STORETYPE_ID, TDBStoreTypes.C_TYPE)
    }
}