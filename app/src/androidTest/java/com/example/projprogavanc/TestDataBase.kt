package com.example.projprogavanc

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.projprogavanc.DB.*

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
        val GameStore = TDBGame_Store(db).insert(gameStore.toContentValues())
        assertNotEquals(-1,GameStore)
    }
    private fun insertType(db: SQLiteDatabase, type: Type) {
        type.id = TDBTypes(db).insert(type.toContentValues())
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

        insertType(db,Type("Digital"))

        db.close()
    }

    @Test
    fun AlterTypeTest() {

        val db = getWritableDB()

        val type = Type("Physical")
        insertType(db, type)

        type.type = "Collectors Edition"

        val alteredData = TDBTypes(db).update(
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

        val type = Type("Collectors Edition")
        insertType(db, type)

        val deletedData = TDBTypes(db).delete(
            "${BaseColumns._ID} = ?",
            arrayOf("${type.id}")
        )

        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun QueryTypeTest() {

        val db = getWritableDB()

        val type = Type("Pre-order")
        insertType(db, type)

        val cursor = TDBTypes(db).query(
            TDBTypes.ALL_COLUMNS,
            "${BaseColumns._ID} = ?",
            arrayOf("${type.id}"),
            null,
            null,
            null

        )

        assertEquals(1, cursor.count)

        assertTrue(cursor.moveToNext())

        val typeDB = Type.fromCursor(cursor)

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

        insertGame(db, Game("Doom", "Digital"))

        db.close()
    }

    @Test
    fun insertStoreTest() {
        val db = getWritableDB()

        insertStore(db, Store("Steam", "N/A", "Digital"))

        db.close()
    }

    @Test
    fun insertGame_StoreTest() {
        val db = getWritableDB()

        val game = Game("Bioshock", "Digital")
        insertGame(db, game)

        val store = Store("Epic Games", "N/A", "Digital")
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

        val game = Game("Elder Rings", "Physical")
        insertGame(db, game)

        game.name = "Elden Ring"
        game.type = "Collectors Edition"

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

        val store = Store("Epic Games", "N/A", "Digital")
        insertStore(db, store)

        store.name = "GameStop"
        store.local = "Blanchardstown Centre"
        store.type = "Physical"

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

        val game = Game("Super Meat Boy", "Digital")
        insertGame(db, game)

        val game2 = Game("Hades", "Digital")
        insertGame(db, game2)

        val store1 = Store("Steam", "N/A", "Digital")
        insertStore(db, store1)

        val store2 = Store("Epic Games", "N/A", "Digital")
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

        val game = Game("Doom", "Digital")
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

        val store = Store("Epic Games", "N/A", "Digital")
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

        val game = Game("Don't Starve", "Digital")
        insertGame(db, game)

        val store1 = Store("Steam", "N/A", "Digital")
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

        val game = Game("Borderlands", "Digital")
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

        val store = Store("Kinguin", "N/A", "Digital")
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

        val game = Game("Unpacking","Digital")
        insertGame(db, game)

        val store = Store("Steam","N/A","Digital")
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