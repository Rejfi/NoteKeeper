package com.revolshen.cardviewapp

import android.database.Cursor

class Note{

     var id: Int = -1
     var title:String =""
     var message: String =""

    fun setData(cursor: Cursor) {
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0)
            title = cursor.getString(1)
            message = cursor.getString(2)
        }
    }

    fun getID(): Int{
        return id
    }


}