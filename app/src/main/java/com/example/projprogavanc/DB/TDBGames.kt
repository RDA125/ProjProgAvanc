package com.example.projprogavanc.DB

import android.database.sqlite.SQLiteDatabase
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

    /**
     * Nome das colunas da tabela Games
     */
    companion object{

        const val T_NAME = "Games"
        const val C_NAME = "Name"
        const val C_GAMETYPE_ID = "Type"

        val ALL_COLUMNS = arrayOf(BaseColumns._ID, C_NAME, C_GAMETYPE_ID)
    }
}