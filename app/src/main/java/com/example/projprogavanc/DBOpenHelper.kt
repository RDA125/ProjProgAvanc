package com.example.projprogavanc

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBOpenHelper (context: Context?): SQLiteOpenHelper(context, NAME, null, VERSION) {
    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase?) {

        requireNotNull(db)

        TDBGames(db).create()
        TDBStores(db).create()
        TDBGameTypes(db).create()
        TDBStoreTypes(db).create()
        TDBGame_Store(db).create()

        val GameType1 = GameType("Digital")
        val GameType2 = GameType("Physical")
        val GameType3 = GameType("Collector's Edition")
        val GameType4 = GameType("DLC")
        val StoreType1 = StoreType("Digital")
        val StoreType2 = StoreType("Physical")


        GameType1.id = TDBGameTypes(db).insert(GameType1.toContentValues())
        GameType2.id = TDBGameTypes(db).insert(GameType2.toContentValues())
        GameType3.id = TDBGameTypes(db).insert(GameType3.toContentValues())
        GameType4.id = TDBGameTypes(db).insert(GameType4.toContentValues())

        StoreType1.id = TDBStoreTypes(db).insert(StoreType1.toContentValues())
        StoreType2.id = TDBStoreTypes(db).insert(StoreType2.toContentValues())
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     *
     *
     * The SQLite ALTER TABLE documentation can be found
     * [here](http://sqlite.org/lang_altertable.html). If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     *
     *
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     *
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    companion object{

        const val NAME = "GameStore.db"
        private const val VERSION = 1
    }
}