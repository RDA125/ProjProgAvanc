package com.example.projprogavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns


/**
 * Funções para manipulaçao da tabela Preco
 *
 * @param db Base de Dados a utilizar
 */

class TDBPreco (val db: SQLiteDatabase){

    /**
     * criação a tabela Preco
     */

    fun create(){

        db.execSQL("create table $T_NAME($C_GAME_ID Integer not null, $C_STORE_ID Integer not null, $C_PRECO Real not null, foreign key ($C_GAME_ID) references ${TDBGame.T_NAME}(${BaseColumns._ID})on delete restrict, foreign key ($C_STORE_ID) references ${TDBStore.T_NAME}(${BaseColumns._ID}) on delete restrict, primary key ($C_GAME_ID,$C_STORE_ID))")

    }

    /**
     * Nome das colunas da tabela preco
     */
    companion object{

        const val T_NAME = "Preco"
        const val C_PRECO = "Preco"
        const val C_GAME_ID = "Game_Id"
        const val C_STORE_ID = "Store_id"

    }
}