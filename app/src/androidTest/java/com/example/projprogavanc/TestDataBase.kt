package com.example.projprogavanc

import android.database.sqlite.SQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.projprogavanc.DB.*
import org.junit.After

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

    private fun GetWritableDB(): SQLiteDatabase {
        val openHelper = DBOpenHelper(appContext())
        return openHelper.writableDatabase

    }

    private fun InsertGame(db: SQLiteDatabase, game: Game) {
        game.id = TDBGames(db).insert(game.toContentValues())
        assertNotEquals(-1, game.id)
    }

    private fun InsertStore(db: SQLiteDatabase, store: Store) {
        store.id = TDBStores(db).insert(store.toContentValues())
        assertNotEquals(-1, store.id)
    }

    @Before
    fun DeleteDataBase(){

        appContext().deleteDatabase(DBOpenHelper.NAME)

    }

    @Test
    fun OpenDB_Test(){

        val openHelper = DBOpenHelper(appContext())
        val db = openHelper.readableDatabase

        assertTrue(db.isOpen)
        db.close()

    }

    @Test
    fun InsertGame(){
        val db = GetWritableDB()

        InsertGame(db, Game("Doom","Digital"))

        db.close()
    }

    @Test
    fun InsertStore(){
        val db = GetWritableDB()

        InsertStore(db, Store("Steam","N/A","Digital"))

        db.close()
    }
}