package com.example.projprogavanc.DB

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns


/**
 * Class para item do tipo Type
 *
 * @param type -> tipo (Digital ou física ou outro)
 * @param id -> id do tipo
 *
 */
data class Type(var type : String, var id : Long= -1) {

    fun toContentValues(): ContentValues {

        val values = ContentValues()
        values.put(TDBTypes.C_TYPE, type)

        return values

    }

    companion object{
        fun fromCursor(cursor: Cursor):Type{

            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posType = cursor.getColumnIndex(TDBTypes.C_TYPE)

            val id = cursor.getLong(posId)
            val type = cursor.getString(posType)

            return Type(type,id)
        }

    }
}