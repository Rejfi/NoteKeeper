package com.revolshen.cardviewapp.mainview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.revolshen.cardviewapp.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        take_notes_BT.setOnClickListener{
            val intent = Intent(applicationContext, DetailsActivity::class.java)
            startActivityForResult(intent,0)
        }
    }

    override fun onResume() {
        super.onResume()
         val dbHelper = SQLDataBaseHelper(applicationContext)
         val db = dbHelper.writableDatabase

        val notes = ArrayList<Note>()
        val cursor = db.query(
            TableInfo.TABLE_NAME,
            null, null, null,
            null, null, null)

        if(cursor.count > 0){
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                val note = Note()
                note.id = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.message = cursor.getString(2)
                note.date = cursor.getString(3)
                notes.add(note)
                cursor.moveToNext()
            }
        }
        cursor.close()


        //Wyświetlanie elementów na ekranie głównym - Adapter oraz LayoutManager
        recyler_view.layoutManager = GridLayoutManager(applicationContext,2)
        recyler_view.adapter = CardViewAdapter(applicationContext, notes, db)
        //----------------------------------------------------------------------

    }


}


