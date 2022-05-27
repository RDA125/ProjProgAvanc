package com.example.projprogavanc.DB

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

abstract class TDB (val db: SQLiteDatabase, val name: String){

    abstract fun create()

    fun insert(values: ContentValues) {
        db.insert(name, null, values)
    }

    fun update(values: ContentValues, whereClause: String, WhereArgs: Array<String>) {
        db.update(name, values, whereClause, WhereArgs)
    }

    fun delete(whereClause: String, whereArgs: Array<String>) {
        db.delete(name, whereClause,whereArgs)
    }

    fun query(columns: Array<String>, selection: String, selectionArgs: Array<String>, groupBy: String, having: String, orderBy: String) {
        db.query(name, columns, selection, selectionArgs, groupBy, having, orderBy)
    }
}