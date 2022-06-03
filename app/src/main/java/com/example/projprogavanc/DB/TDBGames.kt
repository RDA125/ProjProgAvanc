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

        db.execSQL("create table $T_NAME (${BaseColumns._ID} Integer primary key autoincrement, $C_NAME text not null, $C_TYPE Text not null)")

    }

    /**
     * Nome das colunas da tabela Games
     */
    companion object{

        const val T_NAME = "Games"
        const val C_NAME = "Name"
        const val C_TYPE = "Type"
    }
}