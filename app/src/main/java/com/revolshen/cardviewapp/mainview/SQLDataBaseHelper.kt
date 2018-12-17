package com.revolshen.cardviewapp.mainview

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/*
Podstawowe informacje o kolumnach tabeli
 */
object TableInfo : BaseColumns {
        const val TABLE_NAME = "MyNotes"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_MESSAGE = "message"
        const val COLUMN_NAME_DATE = "date"
}
/*
Podstawowe komendy MySQL do zarządzania bazą danych
1. CREATE TABLE
2. DELETE TABLE
 */
object BasicComannd {
     const val SQL_CREATE_TABLE =
        "CREATE TABLE ${TableInfo.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${TableInfo.COLUMN_NAME_TITLE} TEXT NOT NULL," +
                "${TableInfo.COLUMN_NAME_MESSAGE} TEXT NOT NULL, " +
                "${TableInfo.COLUMN_NAME_DATE} TEXT)"

     const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TableInfo.TABLE_NAME}"

}

class SQLDataBaseHelper(context: Context): SQLiteOpenHelper(context,
    TableInfo.TABLE_NAME, null, 1 ){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(BasicComannd.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(BasicComannd.SQL_DELETE_TABLE)
        onCreate(db)

    }

}


