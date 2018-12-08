package com.revolshen.cardviewapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import kotlinx.android.synthetic.main.activity_details.*
import android.view.Menu
import android.view.MenuItem

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if(intent.hasExtra("title")) title_details.setText(intent.getStringExtra("title"))
        if(intent.hasExtra("message")) message_details.setText(intent.getStringExtra("message"))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.save){
            val dbHelper = SQLDataBaseHelper(applicationContext)
            val db = dbHelper.writableDatabase

            //-----------Zapisywanie treści---------------------------------
            val title = title_details.text.toString()
            val message = message_details.text.toString()
            val values = ContentValues()
            values.put("title", title)
            values.put("message", message)

            if (intent.hasExtra("ID")) {
                db.update(TableInfo.TABLE_NAME,values,
                    BaseColumns._ID + "=?", arrayOf(intent.getStringExtra("ID")))

            } else {
                db.insertOrThrow(TableInfo.TABLE_NAME, null, values)
            }
            db.close()
            onBackPressed()
            //-----------------------------------------------------------------------------
        }
        return super.onOptionsItemSelected(item)
    }

}


