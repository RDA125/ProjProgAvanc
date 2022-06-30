package com.example.projprogavanc

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

/**
 * Class para item do tipo Game_Store
 *
 * @param preco -> Preço do jogo dependendo da loja
 * @param game -> onde vamos buscar o Id do jogo da tabela Games com o uso do inner join
 * @param store -> onde vamos buscar o Id Id da loja da tabela Stores com o uso do inner join
 *
 */

data class Game_Store(var preco: Double,var game :Game, var store :Store, var id:Long = -1):Serializable {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBGame_Store.C_PRECO, preco)
        values.put(TDBGame_Store.C_GAME_ID, game.id)
        values.put(TDBGame_Store.C_STORE_ID, store.id)

        return values

    }

    companion object{
        fun fromCursor(cursor: Cursor): Game_Store {

            val posRowId = cursor.getColumnIndex(BaseColumns._ID)
            val posPrice = cursor.getColumnIndex(TDBGame_Store.C_PRECO)
            val posGameId = cursor.getColumnIndex(TDBGame_Store.C_GAME_ID)
            val posStoreId = cursor.getColumnIndex(TDBGame_Store.C_STORE_ID)
            val posGameName = cursor.getColumnIndex(TDBGames.C_NAME)
            val posGameTypeid = cursor.getColumnIndex(TDBGames.C_GAMETYPE_ID)
            val posStoreName = cursor.getColumnIndex(TDBStores.C_NAME)
            val posStoreAddress = cursor.getColumnIndex(TDBStores.C_ADDRESS)
            val posStoreTypeid = cursor.getColumnIndex(TDBStores.C_STORETYPE_ID)

            val posGameTypeName = cursor.getColumnIndex(TDBGameTypes.C_TYPE)
            val posStoreTypeName = cursor.getColumnIndex(TDBStoreTypes.C_TYPE)

            val rowid = cursor.getLong((posRowId))
            val price = cursor.getDouble(posPrice)
            val gameId = cursor.getLong(posGameId)
            val storeId = cursor.getLong(posStoreId)
            val gameName = cursor.getString(posGameName)
            val gameTypeid = cursor.getLong(posGameTypeid)
            val storeName = cursor.getString(posStoreName)
            val storeAddress = cursor.getString(posStoreAddress)
            val storeTypeid = cursor.getLong(posStoreTypeid)

            val gameTypeName = cursor.getString(posGameTypeName)
            val storeTypeName = cursor.getString(posStoreTypeName)

            val gameType = GameType( gameTypeName,gameTypeid)
            val storeType = StoreType( storeTypeName,storeTypeid)

            val game = Game(gameName, gameType,gameId)
            val store = Store(storeName, storeAddress, storeType,storeId)

            return Game_Store(price,game,store,rowid)
        }

    }
}