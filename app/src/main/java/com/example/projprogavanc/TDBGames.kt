package com.example.projprogavanc

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

/**
 * Funções para manipulaçao da tabela Game
 *
 * @param db Base de Dados a utilizar
 */

class TDBGames(db: SQLiteDatabase) : TDB(db, T_NAME) {
    /**
     * criação da tabela Games
     */

    override fun create(){

        db.execSQL("create table $T_NAME (${BaseColumns._ID} Integer primary key autoincrement, $C_NAME text not null, $C_GAMETYPE_ID Text not null, foreign key($C_GAMETYPE_ID) references ${TDBGameTypes.T_NAME} (${BaseColumns._ID}) on delete restrict)")

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

        queryBuilder.tables = "$T_NAME inner join ${TDBGameTypes.T_NAME} on $C_GAMETYPE_ID = ${TDBGameTypes.C_ID} "

        //TODO("wait to finish queries")

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    /**
     * Nome das colunas da tabela Games
     */
    companion object{

        const val T_NAME = "Games"
        const val C_ID = "$T_NAME.${BaseColumns._ID}"
        const val C_NAME = "Name"
        const val C_GAMETYPE_ID = "Type"

        val ALL_COLUMNS = arrayOf(C_ID, C_NAME, C_GAMETYPE_ID)
    }
}