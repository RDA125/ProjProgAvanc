package com.example.projprogavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.projprogavanc.DB.*
import com.example.projprogavanc.DB.Interface.DBOpenHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestDataBase {
    fun appContext() = InstrumentationRegistry.getInstrumentation().targetContext

    /**
     *
     * função que devolve a base de dados para escrever o conteudo necessário
     */
    private fun getWritableDB(): SQLiteDatabase {
        val openHelper = DBOpenHelper(appContext())
        return openHelper.writableDatabase

    }

    /**
     * Funções para inserir Game, Store e Game_Store
     */
    private fun insertGame(db: SQLiteDatabase, game: Game) {
        game.id = TDBGames(db).insert(game.toContentValues())
        assertNotEquals(-1, game.id)
    }

    private fun insertStore(db: SQLiteDatabase, store: Store) {
        store.id = TDBStores(db).insert(store.toContentValues())
        assertNotEquals(-1, store.id)
    }

    private fun insertGameStore(db: SQLiteDatabase, gameStore: Game_Store) {
        val gameStore = TDBGame_Store(db).insert(gameStore.toContentValues())
        assertNotEquals(-1,gameStore)
    }
    private fun insertGameType(db: SQLiteDatabase, type: GameType) {
        type.id = TDBStoreTypes(db).insert(type.toContentValues())
        assertNotEquals(-1, type.id)
    }

    private fun insertStoreType(db: SQLiteDatabase, type: StoreType) {
        type.id = TDBStoreTypes(db).insert(type.toContentValues())
        assertNotEquals(-1, type.id)
    }

    @Before
    fun deleteDataBase() {

        appContext().deleteDatabase(DBOpenHelper.NAME)

    }

    @Test
    fun openDB_Test() {

        val openHelper = DBOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)
        db.close()

    }

    /**
     *
     * Secção de Testes para a tabela Types
     *
     */

    @Test
    fun InsertTypeTest(){

        val db = getWritableDB()

        insertGameType(db,GameType("Digital"))

        db.close()
    }

    @Test
    fun AlterTypeTest() {

        val db = getWritableDB()

        val type = GameType("Physical")
        insertGameType(db, type)

        type.type = "Collectors Edition"

        val alteredData = TDBStoreTypes(db).update(
            type.toContentValues(),
            "${BaseColumns._ID} = ?",
            arrayOf("${type.id}")
        )

        assertEquals(1, alteredData)

        db.close()
    }

    @Test
    fun DeleteTypeTest() {
        val db = getWritableDB()

        val type = GameType("Collectors Edition")
        insertGameType(db, type)

        val deletedData = TDBStoreTypes(db).delete(
            "${BaseColumns._ID} = ?",
            arrayOf("${type.id}")
        )

        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun QueryTypeTest() {

        val db = getWritableDB()

        val type = StoreType("Pre-order")
        insertStoreType(db, type)

        val cursor = TDBStoreTypes(db).query(
            TDBStoreTypes.ALL_COLUMNS,
            "${BaseColumns._ID} = ?",
            arrayOf("${type.id}"),
            null,
            null,
            null

        )

        assertEquals(1, cursor.count)

        assertTrue(cursor.moveToNext())

        val typeDB = StoreType.fromCursor(cursor)

        assertEquals(type, typeDB)

        db.close()
    }

    /**
     *
     * Secção de Testes para verificar se é possivel inserir Game, Store e Game_store
     *
     */
    @Test
    fun insertGameTest() {
        val db = getWritableDB()

        val GameType =GameType("Digital")
        insertGameType(db,GameType)
        insertGame(db, Game("Doom", GameType.id))

        db.close()
    }

    @Test
    fun insertStoreTest() {
        val db = getWritableDB()
        val StoreType =GameType("Digital")
        insertGameType(db,StoreType)
        insertStore(db, Store("Steam", "N/A", StoreType.id))

        db.close()
    }

    @Test
    fun insertGame_StoreTest() {
        val db = getWritableDB()

        val GameType =GameType("Digital")
        insertGameType(db,GameType)

        val game = Game("Bioshock", GameType.id)
        insertGame(db, game)

        val StoreType =GameType("Digital")
        insertGameType(db,StoreType)

        val store = Store("Epic Games", "N/A", StoreType.id)
        insertStore(db, store)

        val gameStore = Game_Store(29.99, game.id, store.id)
        insertGameStore(db, gameStore)

        db.close()
    }

    /**
     *
     * Secção de Testes para a alteração de Game, Store e Game_Store
     *
     */

    @Test
    fun AlterGameTest() {

        val db = getWritableDB()

        val GameType1 =GameType("Physical")
        insertGameType(db,GameType1)

        val GameType2 =GameType("Collectors Edition")
        insertGameType(db,GameType2)

        val game = Game("Elder Rings", GameType1.id)
        insertGame(db, game)

        game.name = "Elden Ring"
        game.type = GameType2.id

        val alteredData = TDBGames(db).update(
            game.toContentValues(),
            "${BaseColumns._ID} = ?",
            arrayOf("${game.id}")
        )

        assertEquals(1, alteredData)

        db.close()
    }

    @Test
    fun AlterStoreTest() {

        val db = getWritableDB()

        val StoreType1 =GameType("Digital")
        insertGameType(db,StoreType1)

        val StoreType2 =GameType("Physical")
        insertGameType(db,StoreType2)

        val store = Store("Epic Games", "N/A", StoreType1.id)
        insertStore(db, store)

        store.name = "GameStop"
        store.address = "Blanchardstown Centre"
        store.type = StoreType2.id

        val alteredData = TDBStores(db).update(
            store.toContentValues(),
            "${BaseColumns._ID} = ?",
            arrayOf("${store.id}")
        )

        assertEquals(1, alteredData)

        db.close()
    }

    @Test
    fun AlterGameStoreTest() {

        val db = getWritableDB()

        val StoreType =GameType("Digital")
        insertGameType(db,StoreType)

        val GameType =GameType("Digital")
        insertGameType(db,GameType)

        val game = Game("Super Meat Boy", GameType.id)
        insertGame(db, game)

        val game2 = Game("Hades",  GameType.id)
        insertGame(db, game2)

        val store1 = Store("Steam", "N/A", StoreType.id)
        insertStore(db, store1)

        val store2 = Store("Epic Games", "N/A", StoreType.id)
        insertStore(db, store2)

        val gameStore = Game_Store(14.99, game.id, store1.id)
        insertGameStore(db, gameStore)

        val gameStore2 = Game_Store(14.99, game.id, store2.id)
        insertGameStore(db, gameStore2)

        gameStore2.preco = 19.99

        val alteredData = TDBGame_Store(db).update(
            gameStore2.toContentValues(),
            "${TDBGame_Store.C_GAME_ID} = ? AND ${TDBGame_Store.C_STORE_ID} = ?",
            arrayOf("${gameStore2.game_id}", "${gameStore2.store_id}")
        )

        assertEquals(1, alteredData)

        db.close()

    }

    /**
     *
     * Secção de Testes para a eliminação de Game, Store e Game_Store
     *
     */

    @Test
    fun DeleteGameTest() {
        val db = getWritableDB()

        val GameType =GameType("Digital")
        insertGameType(db,GameType)

        val game = Game("Doom", GameType.id)
        insertGame(db, game)

        val deletedData = TDBGames(db).delete(
            "${BaseColumns._ID} = ?",
            arrayOf("${game.id}")
        )

        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun DeleteStoreTest() {

        val db = getWritableDB()

        val StoreType =StoreType("Digital")
        insertStoreType(db,StoreType)

        val store = Store("Epic Games", "N/A", StoreType.id)
        insertStore(db, store)

        val deletedData = TDBStores(db).delete(
            "${BaseColumns._ID} = ?",
            arrayOf("${store.id}")
        )

        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun deleteGameStoreTest() {

        val db = getWritableDB()

        val GameType =GameType("Digital")
        insertGameType(db,GameType)

        val StoreType =StoreType("Digital")
        insertStoreType(db,StoreType)

        val game = Game("Don't Starve", GameType.id)
        insertGame(db, game)

        val store1 = Store("Steam", "N/A", StoreType.id)
        insertStore(db, store1)


        val gameStore = Game_Store(8.19, game.id, store1.id)
        insertGameStore(db, gameStore)


        val deletedData = TDBGame_Store(db).delete(
            "${TDBGame_Store.C_GAME_ID} = ? AND ${TDBGame_Store.C_STORE_ID} = ?",
            arrayOf("${gameStore.game_id}", "${gameStore.store_id}")
        )

        assertEquals(1, deletedData)

        db.close()

    }

    /**
     *
     * Secção de Testes para a leitura de Game, Store e Game_Store
     *
     */

    @Test
    fun QueryGameTest() {

        val db = getWritableDB()
        val GameType =GameType("Digital")
        insertGameType(db,GameType)

        val game = Game("Borderlands", GameType.id)
        insertGame(db, game)

        val cursor = TDBGames(db).query(
            TDBGames.ALL_COLUMNS,
            "${BaseColumns._ID} = ?",
            arrayOf("${game.id}"),
            null,
            null,
            null

        )

        assertEquals(1, cursor.count)

        assertTrue(cursor.moveToNext())

        val gameDB = Game.fromCursor(cursor)

        assertEquals(game, gameDB)

        db.close()
    }

    @Test
    fun QueryStoreTest() {

        val db = getWritableDB()

        val StoreType =StoreType("Digital")
        insertStoreType(db,StoreType)

        val store = Store("Kinguin", "N/A", StoreType.id)
        insertStore(db, store)

        val cursor = TDBStores(db).query(
            TDBStores.ALL_COLUMNS,
            "${BaseColumns._ID} = ?",
            arrayOf("${store.id}"),
            null,
            null,
            null

        )

        assertEquals(1, cursor.count)

        assertTrue(cursor.moveToNext())

        val storeDB = Store.fromCursor(cursor)

        assertEquals(store, storeDB)

        db.close()
    }

    @Test
    fun QueryGameStoreTest(){

        val db = getWritableDB()

        val GameType =GameType("Digital")
        insertGameType(db,GameType)

        val StoreType =StoreType("Digital")
        insertStoreType(db,StoreType)

        val game = Game("Unpacking",GameType.id)
        insertGame(db, game)

        val store = Store("Steam","N/A",StoreType.id)
        insertStore(db, store)

        val gameStore = Game_Store(19.99, game.id, store.id)
        insertGameStore(db,gameStore)

        val cursor = TDBGame_Store(db).query(
            TDBGame_Store.ALL_COLUMNS,
            "${TDBGame_Store.C_GAME_ID} = ? AND ${TDBGame_Store.C_STORE_ID} = ?",
            arrayOf("${gameStore.game_id}","${gameStore.store_id}"),
            null,
            null,
            null

        )

        assertEquals(1, cursor.count)

        assertTrue(cursor.moveToNext())

        val GameStoreDB = Game_Store.fromCursor(cursor)

        assertEquals(gameStore, GameStoreDB)

        db.close()
    }

}