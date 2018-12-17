package com.revolshen.cardviewapp.mainview

import android.app.ActionBar
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import kotlinx.android.synthetic.main.activity_details.*
import android.view.Menu
import android.view.MenuItem
import com.revolshen.cardviewapp.R
import java.text.DateFormat
import java.text.Format
import java.util.*

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
            val dateFormat = DateFormat.getDateInstance()
            val date = dateFormat.format(Date().time)

            val values = ContentValues().apply {
                put(TableInfo.COLUMN_NAME_TITLE, title)
                put(TableInfo.COLUMN_NAME_MESSAGE, message)
                put(TableInfo.COLUMN_NAME_DATE, date)
            }


            if (intent.hasExtra("ID")) {
                db.update(
                    TableInfo.TABLE_NAME,values,
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


