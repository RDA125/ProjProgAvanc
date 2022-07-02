package com.example.projprogavanc

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

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

    private fun insertGameStore(db: SQLiteDatabase, gameStore: GameStore) {
        val gameStore = TDBGame_Store(db).insert(gameStore.toContentValues())
        assertNotEquals(-1,gameStore)
    }
    private fun insertGameType(db: SQLiteDatabase, type: GameType) {
        type.id = TDBGameTypes(db).insert(type.toContentValues())
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

        insertGameType(db, GameType("Digital"))

        db.close()
    }

    @Test
    fun AlterTypeTest() {

        val db = getWritableDB()

        val type = GameType("Physical")
        insertGameType(db, type)

        type.type = "Collectors Edition"

        val alteredData = TDBGameTypes(db).update(
            type.toContentValues(),
            "${TDBGameTypes.C_ID} = ?",
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

        val deletedData = TDBGameTypes(db).delete(
            "${TDBGameTypes.C_ID} = ?",
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
            "${TDBStoreTypes.C_ID} = ?",
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

        val gameType = GameType("Digital")
        insertGameType(db,gameType)
        insertGame(db, Game("Doom", gameType))

        db.close()
    }

    @Test
    fun insertStoreTest() {
        val db = getWritableDB()
        val storeType = StoreType("Digital")
        insertStoreType(db,storeType)
        insertStore(db, Store("Steam", "N/A", storeType))

        db.close()
    }

    @Test
    fun insertGame_StoreTest() {
        val db = getWritableDB()

        val gameType = GameType("Digital")
        insertGameType(db,gameType)

        val game = Game("Bioshock", gameType)
        insertGame(db, game)

        val storeType = StoreType("Digital")
        insertStoreType(db,storeType)

        val store = Store("Epic Games", "N/A", storeType)
        insertStore(db, store)

        val gameStore = GameStore(29.99, game, store)
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

        val gameType1 = GameType("Physical")
        insertGameType(db,gameType1)

        val gameType2 = GameType("Collectors Edition")
        insertGameType(db,gameType2)

        val game = Game("Elder Rings", gameType1)
        insertGame(db, game)

        game.name = "Elden Ring"
        game.type = gameType2

        val alteredData = TDBGames(db).update(
            game.toContentValues(),
            "${TDBGames.C_ID} = ?",
            arrayOf("${game.id}")
        )

        assertEquals(1, alteredData)

        db.close()
    }

    @Test
    fun AlterStoreTest() {

        val db = getWritableDB()

        val storeType1 = StoreType("Digital")
        insertStoreType(db,storeType1)

        val storeType2 = StoreType("Physical")
        insertStoreType(db,storeType2)

        val store = Store("Epic Games", "N/A", storeType1)
        insertStore(db, store)

        store.name = "GameStop"
        store.address = "Blanchardstown Centre"
        store.type = storeType2

        val alteredData = TDBStores(db).update(
            store.toContentValues(),
            "${TDBStores.C_ID} = ?",
            arrayOf("${store.id}")
        )

        assertEquals(1, alteredData)

        db.close()
    }

    @Test
    fun AlterGameStoreTest() {

        val db = getWritableDB()

        val storeType = StoreType("Digital")
        insertStoreType(db,storeType)

        val gameType = GameType("Digital")
        insertGameType(db,gameType)

        val game = Game("Super Meat Boy", gameType)
        insertGame(db, game)

        val game2 = Game("Hades",  gameType)
        insertGame(db, game2)

        val store1 = Store("Steam", "N/A", storeType)
        insertStore(db, store1)

        val store2 = Store("Epic Games", "N/A", storeType)
        insertStore(db, store2)

        val gameStore = GameStore(14.99, game, store1)
        insertGameStore(db, gameStore)

        val gameStore2 = GameStore(14.99, game, store2)
        insertGameStore(db, gameStore2)

        gameStore2.preco = 19.99

        val alteredData = TDBGame_Store(db).update(
            gameStore2.toContentValues(),
            "${TDBGame_Store.C_GAME_ID} = ? AND ${TDBGame_Store.C_STORE_ID} = ?",
            arrayOf("${gameStore2.game.id}", "${gameStore2.store.id}")
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

        val gameType = GameType("Digital")
        insertGameType(db,gameType)

        val game = Game("Doom", gameType)
        insertGame(db, game)

        val deletedData = TDBGames(db).delete(
            "${TDBGames.C_ID} = ?",
            arrayOf("${game.id}")
        )

        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun DeleteStoreTest() {

        val db = getWritableDB()

        val storeType = StoreType("Digital")
        insertStoreType(db,storeType)

        val store = Store("Epic Games", "N/A", storeType)
        insertStore(db, store)

        val deletedData = TDBStores(db).delete(
            "${TDBStores.C_ID} = ?",
            arrayOf("${store.id}")
        )

        assertEquals(1, deletedData)

        db.close()
    }

    @Test
    fun deleteGameStoreTest() {

        val db = getWritableDB()

        val gameType = GameType("Digital")
        insertGameType(db,gameType)

        val storeType = StoreType("Digital")
        insertStoreType(db,storeType)

        val game = Game("Don't Starve", gameType)
        insertGame(db, game)

        val store1 = Store("Steam", "N/A", storeType)
        insertStore(db, store1)


        val gameStore = GameStore(8.19, game, store1)
        insertGameStore(db, gameStore)


        val deletedData = TDBGame_Store(db).delete(
            "${TDBGame_Store.C_GAME_ID} = ? AND ${TDBGame_Store.C_STORE_ID} = ?",
            arrayOf("${gameStore.game.id}", "${gameStore.store.id}")
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
        val gameType = GameType("Digital")
        insertGameType(db,gameType)

        val game = Game("Borderlands", gameType)
        insertGame(db, game)

        val cursor = TDBGames(db).query(
            TDBGames.ALL_COLUMNS,
            "${TDBGames.C_ID} = ?",
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

        val storeType = StoreType("Digital")
        insertStoreType(db,storeType)

        val store = Store("Kinguin", "N/A", storeType)
        insertStore(db, store)

        val cursor = TDBStores(db).query(
            TDBStores.ALL_COLUMNS,
            "${TDBStores.C_ID} = ?",
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

        val gameType = GameType("Digital")
        insertGameType(db,gameType)

        val storeType = StoreType("Digital")
        insertStoreType(db,storeType)

        val game = Game("Unpacking",gameType)
        insertGame(db, game)

        val store = Store("Steam","N/A",storeType)
        insertStore(db, store)

        val gameStore = GameStore(19.99, game, store)
        insertGameStore(db,gameStore)

        val cursor = TDBGame_Store(db).query(
            TDBGame_Store.ALL_COLUMNS,
            "${TDBGame_Store.C_GAME_ID} = ? AND ${TDBGame_Store.C_STORE_ID} = ?",
            arrayOf("${gameStore.game.id}","${gameStore.store.id}"),
            null,
            null,
            null

        )

        /*val cursor = TDBGame_Store(db).query(
            TDBGame_Store.ALL_COLUMNS,
            "${TDBGame_Store.C_ID} = ?",
            arrayOf("${gameStore.id}"),
            null,
            null,
            null

        )*/

        assertEquals(1, cursor.count)

        assertTrue(cursor.moveToNext())
       // cursor.columnNames[0] = "_id" //This a compromise until i find a better solution
        val gameStoreDB = GameStore.fromCursor(cursor)

        assertEquals(gameStore, gameStoreDB)

        db.close()
    }

}