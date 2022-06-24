package com.example.projprogavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns


/**
 * Funções para manipulaçao da tabela Preco
 *
 * @param db Base de Dados a utilizar
 */

class TDBGame_Store (db: SQLiteDatabase): TDB(db, T_NAME){

    /**
     * criação a tabela de relacionamento Game_Store
     */

    override fun create(){

        db.execSQL("create table $T_NAME($C_GAME_ID Integer not null, $C_STORE_ID Integer not null, $C_PRECO Real not null, foreign key ($C_GAME_ID) references ${TDBGames.T_NAME}(${BaseColumns._ID})on delete restrict, foreign key ($C_STORE_ID) references ${TDBStores.T_NAME}(${BaseColumns._ID}) on delete restrict, primary key ($C_GAME_ID,$C_STORE_ID))")

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

        queryBuilder.tables = "$T_NAME inner join (${TDBGames.T_NAME} inner join ${TDBGameTypes.T_NAME} on ${TDBGames.C_GAMETYPE_ID} = ${TDBGameTypes.C_ID} ) on $C_GAME_ID = ${TDBGames.C_ID} inner join (${TDBStores.T_NAME} inner join ${TDBStoreTypes.T_NAME} on ${TDBStores.C_STORETYPE_ID} = ${TDBStoreTypes.C_ID}) on $C_STORE_ID = ${TDBStores.C_ID}"



        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }


    /**
     * Nome das colunas da tabela Game_Store
     */
    companion object{

        const val T_NAME = "Game_Store"
        const val C_PRECO = "Preco"
        const val C_GAME_ID = "Game_Id"
        const val C_STORE_ID = "Store_id"

        val ALL_COLUMNS = arrayOf(C_GAME_ID, C_STORE_ID, C_PRECO, TDBGames.C_NAME, TDBGames.C_GAMETYPE_ID, TDBGameTypes.C_TYPE, TDBStores.C_NAME, TDBStores.C_ADDRESS, TDBStores.C_STORETYPE_ID, TDBStoreTypes.C_TYPE)
    }
}