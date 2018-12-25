package com.revolshen.cardviewapp.mainview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import com.revolshen.cardviewapp.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var oldPosition = MotionEvent.PointerCoords()
    private var newPosition = MotionEvent.PointerCoords()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    /*
        take_notes_BT.setOnClickListener{
            val intent = Intent(applicationContext, DetailsActivity::class.java)
            startActivityForResult(intent,0)
        }
    */
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(MotionEvent.ACTION_DOWN == event?.actionMasked){
            oldPosition.x = event.getX(event.actionIndex)
            oldPosition.y = event.getY(event.actionIndex)
        }

        when(event!!.actionMasked){
            MotionEvent.ACTION_UP-> {
                newPosition.x = event.getX(event.actionIndex)
                newPosition.y = event.getY(event.actionIndex)

                if((oldPosition.x < newPosition.x) &&
                    (oldPosition.y * 1.2f > newPosition.y) &&
                    (oldPosition.y * 0.8f < newPosition.y)) {

                    val intent = Intent(applicationContext, DetailsActivity::class.java)
                    startActivityForResult(intent,0)
                }
                if((oldPosition.x > newPosition.x) &&
                    (oldPosition.y * 1.2f > newPosition.y) &&
                    (oldPosition.y * 0.8f < newPosition.y)) {

                    val intent = Intent(applicationContext, DetailsActivity::class.java)
                    startActivityForResult(intent,0)
                }

            }
        }

        return false
    }
}


